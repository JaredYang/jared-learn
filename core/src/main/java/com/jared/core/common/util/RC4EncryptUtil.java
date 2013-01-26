package com.jared.core.common.util;

/**
 * User: haluo
 * Date: 2010-4-2
 * Time: 13:34:32
 */

/**
 * The implementation of stream symmetric algorithm RC4.
 */
public final class RC4EncryptUtil {
    private static final int STATE_LENGTH = 256;
    /**
     * A private constructor to prevent an instance to be create directly.
     */
    private RC4EncryptUtil() {
    }

    /**
     * Return the encrypted text.
     * @param plainText  the text need to be encrypted.
     * @param key        the key will be converted to a byte array under utf-8 character set.
     * @return           the encrypted String.
     */
    public static String encrypt(String plainText,String key) {
        try {
            return Base64.encode(RC4EncryptUtil.encrypt(key.getBytes("UTF-8"), plainText.getBytes("UTF-8"))).replaceAll("/", "_").replaceAll("\\+","-").replaceAll("=","*");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
      * encrypt plainData using the key.
      * @param key       The key to encrypt the data.
      * @param plainData A plainData to be encrypted.
      * @return An array represented the cipher data.
      */
    public static byte[] encrypt(byte[] key, byte[] plainData) {
        //Initialize the statebox and keybox.
        //The keybox will be filled using the key indicating by the parameter.
        int[] intKey = fromByteArray(key);
        int[] stateBox = new int[STATE_LENGTH];
        int[] keyBox = new int[STATE_LENGTH];
        int i = 0, j;
        for (; i < STATE_LENGTH; i++) {
            stateBox[i] = i;
            keyBox[i] = intKey[i % key.length];
        }

        //Permute the statebox using the value of keybox.
        i = 0;
        j = 0;
        int temp;
        for (; i < STATE_LENGTH; i++) {
            j = (j + stateBox[i] + keyBox[i]) % STATE_LENGTH;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
        }

        //Generate key stream from the statebox and encrypt data.
        i = 0;
        j = 0;
        int tempKey;
        byte[] cipher = new byte[plainData.length];
        for (int k = 0; k < plainData.length; k++) {
            i = (i + 1) % STATE_LENGTH;
            j = (j + stateBox[i]) % STATE_LENGTH;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
            //Get the encrypt key.
            tempKey = stateBox[(stateBox[i] + stateBox[j]) % STATE_LENGTH];
            //encrypt data and store the cipher in the cipher array.
            cipher[k] = (byte) (tempKey ^ plainData[k]);
        }
        return cipher;
    }

    /**
     * Decrypt the cipherText.
     * @param cipherText   the text needed to be decrypted.
     * @param key          the key used to do the decryption.
     * @return             the plainText after decryption.
     */
    public static String decrypt(String cipherText, String key) {
        try {
            return new String(RC4EncryptUtil.decrypt(key.getBytes("UTF-8"), Base64.decode(cipherText.replaceAll("_", "/").replaceAll("-","+").replaceAll("\\*","="))), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * decrypt cipherData using the key.
     * @param key        The key to decrypt the data.
     * @param cipherData A cipherData to be decrypted.
     * @return An array represented the plain data.
     */
    public static byte[] decrypt(byte[] key, byte[] cipherData) {
        return encrypt(key, cipherData);
    }

    private static int[] fromByteArray(byte[] array){
        int[] intArray = new int[array.length];
        for(int i = 0 ; i < array.length ; i++){
            intArray[i] = array[i] >= 0 ? array[i] : (array[i] + 256);
        }
        return intArray;
    }
}
