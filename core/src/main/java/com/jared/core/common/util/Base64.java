package com.jared.core.common.util;

import java.util.Arrays;

/**
 * User: hui.ouyang
 * Date: 2010-3-3
 * Time: 14:29:18
 */
public class Base64 {
    /**
     * Compared with standard base64,'/' will be replaced with '_',
     * '+' will be replaced with '-','=' will be replaced with '*'
     * @param data to be encoded.
     * @return A string after encoding.
     */
    public static String encodeWithDajieSpec(byte[] data){
        if(data == null || data.length == 0){
            return null;
        }
        return encode(data).replaceAll("/", "_").replaceAll("\\+","-").replaceAll("=","*");
    }

    /**
     * Compared with standard base64,'/' will be replaced with '_',
     * '+' will be replaced with '-','=' will be replaced with '*'
     * @param base64StringWithDajieSpec data to be decode.
     * @return An array after decode.
     */
    public static byte[] decodeWithDajieSpec(String base64StringWithDajieSpec) {
        if(StringUtil.isEmpty(base64StringWithDajieSpec)){
            return null;
        }
        try {
            return decode(base64StringWithDajieSpec.replaceAll("_", "/").replaceAll("-", "+").replaceAll("\\*", "="));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * returns an array of base64-encoded characters to represent the passed
     * data array.
     *
     * @param  dataStr  the array of bytes to encode
     * @return base64-coded character array.
     */
    static public String encode(String dataStr){
        byte[] data= dataStr.getBytes();
        /*
         * int str_len = dataStr.length(); for(int i=0;i<str_len;i++){
         * data[i]=dataStr.getBytes(); }
         */
        char[] out= new char[ ( (data.length+2 )/3 )*4];

        //
        // 3 bytes encode to 4 chars. Output is always an even
        // multiple of 4 characters.
        //
        for(int i= 0, index= 0; i<data.length; i+= 3, index+= 4){
            boolean quad= false;
            boolean trip= false;

            int val= (0xFF&(int) data[i] );
            val<<= 8;
            if( (i+1 )<data.length){
                val|= (0xFF&(int) data[i+1] );
                trip= true;
            }
            val<<= 8;
            if( (i+2 )<data.length){
                val|= (0xFF&(int) data[i+2] );
                quad= true;
            }
            out[index+3]= alphabet[ (quad ? (val&0x3F ) : 64 )];
            val>>= 6;
            out[index+2]= alphabet[ (trip ? (val&0x3F ) : 64 )];
            val>>= 6;
            out[index+1]= alphabet[val&0x3F];
            val>>= 6;
            out[index]= alphabet[val&0x3F];
        }

        return new String(out);
    }

    static public String encode(byte[] data){
        //dataStr.getBytes();
        /*
         * int str_len = dataStr.length(); for(int i=0;i<str_len;i++){
         * data[i]=dataStr.getBytes(); }
         */
        char[] out= new char[ ( (data.length+2 )/3 )*4];

        //
        // 3 bytes encode to 4 chars. Output is always an even
        // multiple of 4 characters.
        //
        for(int i= 0, index= 0; i<data.length; i+= 3, index+= 4){
            boolean quad= false;
            boolean trip= false;

            int val= (0xFF&(int) data[i] );
            val<<= 8;
            if( (i+1 )<data.length){
                val|= (0xFF&(int) data[i+1] );
                trip= true;
            }
            val<<= 8;
            if( (i+2 )<data.length){
                val|= (0xFF&(int) data[i+2] );
                quad= true;
            }
            out[index+3]= alphabet[ (quad ? (val&0x3F ) : 64 )];
            val>>= 6;
            out[index+2]= alphabet[ (trip ? (val&0x3F ) : 64 )];
            val>>= 6;
            out[index+1]= alphabet[val&0x3F];
            val>>= 6;
            out[index]= alphabet[val&0x3F];
        }

        return new String(out);
    }

    /**
     * Returns an array of bytes which were encoded in the passed character
     * array.
     *
     * @param  dataStr the array of base64-encoded characters
     * @return decoded data array
     * @throws Exception Can't be decode
     */
    static public byte[] decode(String dataStr) throws Exception{

        char[] data= dataStr.toCharArray();
        // int Len=dataStr.length();
        // for(int i=0;i<Len;i++){
        // System.out.println("("+data[i]+")");
        // }
        int len= ( (data.length+3 )/4 )*3;
        if(data.length>0&&data[data.length-1]=='=')
            --len;
        if(data.length>1&&data[data.length-2]=='=')
            --len;
        byte[] out= new byte[len];

        int shift= 0; // # of excess bits stored in accum
        int accum= 0; // excess bits
        int index= 0;

        for (char aData : data) {
            int value = codes[aData & 0xFF]; // ignore high byte of char
            if (value >= 0) { // skip over non-code
                accum <<= 6; // bits shift up by 6 each time thru
                shift += 6; // loop, with new bits being put in
                accum |= value; // at the bottom.
                if (shift >= 8) { // whenever there are 8 or more shifted in,
                    shift -= 8; // write them out (from the top, leaving any
                    out[index++] = // excess at the bottom for next iteration.
                            (byte) ((accum >> shift) & 0xff);
                }
            }
        }


        if(out.length>index){
            out = Arrays.copyOfRange(out, 0, index);
        }
        if(index!=out.length){
            //System.out.println("aa-"+out.toString());
            throw new Error("miscalculated data length!");
        }

//        System.out.println(index);
//        System.out.println(out.length);
//        for(byte b : out){
//            System.out.print(b&0xff);
//        }
        return out;
    }

    //
    // code characters for values 0..63
    //
    static private char[] alphabet= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
                    .toCharArray();

    //
    // lookup table for converting base64 characters to value in range 0..63
    //
    static private byte[] codes= new byte[256];
    static{
        for(int i= 0; i<256; i++)
            codes[i]= -1;
        for(int i= 'A'; i<='Z'; i++)
            codes[i]= (byte) (i-'A' );
        for(int i= 'a'; i<='z'; i++)
            codes[i]= (byte) (26+i-'a' );
        for(int i= '0'; i<='9'; i++)
            codes[i]= (byte) (52+i-'0' );
        codes['+']= 62;
        codes['/']= 63;
    }
}
