package com.jared.core.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;


public class DesEncryptUtil {
    private static final Log logger = LogFactory.getLog(DesEncryptUtil.class);
    private static SecretKey deskey;
    private static Cipher c;

    /**
     *
     * @param key       生成密钥长度为7的字串
     * @param plainText 需要加密的明文
     * @param charSet   指定的字符集
     * @return          返回密文
     */
    public static String encrypt(String key, String plainText, String charSet) {
        byte[] keyBytes = getKeyBytes(key);
        if (keyBytes == null) {
            return "";
        } else {
            if (charSet == null) {
                charSet = "utf-8";
            }
            preEncrypt(keyBytes);
            try {
                c.init(Cipher.ENCRYPT_MODE, deskey);
                byte[] src = plainText.getBytes(charSet);
                byte[] enc = c.doFinal(src);
                return bytesToStr(enc);
            } catch (Exception e) {
                logger.error(e);
                return "";
            }
        }
    }

    /**
     *
     * @param key       生成密钥长度为7的字串
     * @param plainText 需要加密的明文
     * @return          返回密文
     */
    public static String encrypt(String key, String plainText){
        return encrypt(key,plainText,null);
    }

    /**
     *
     * @param key       生成密钥长度为7的字串
     * @param hexString 十六进制密文
     * @return          明文
     */
    public static String decrypt(String key,String hexString) {
        byte[] keyBytes = getKeyBytes(key);
        preEncrypt(keyBytes);
        try {
            byte[] enc = StringUtil.hexString2Bytes(hexString);
            c.init(Cipher.DECRYPT_MODE, deskey);
            byte[] dec = c.doFinal(enc);
            return new String(dec);

        } catch (Exception e) {
            logger.error(e);
            return "";
        }
    }

    private static boolean preEncrypt(byte[] keyBytes) {
        try {
            DESKeySpec.isParityAdjusted(keyBytes, 0);
            deskey = new SecretKeySpec(keyBytes, "DES");
            c = Cipher.getInstance("DES");
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    private static byte[] getKeyBytes(String key) {
        if (key!=null && key.matches("[0-9a-zA-Z]{7}")) {
            byte[] result = key.getBytes();
            result = addParity(result);
            return result;
        } else {
            return null;
        }
    }

    private static String bytesToStr(byte[] bytes) {
        String result = "";
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result += temp;
        }
        return result;
    }

    public static byte[] addParity(byte[] in) {
        byte[] result = new byte[8];
        int resultIx = 1;
        int bitCount = 0;
        for (int i = 0; i < 56; i++) {
            boolean bit = (in[6 - i / 8] & (1 << (i % 8))) > 0;
            if (bit) {
                result[7 - resultIx / 8] |= (1 << (resultIx % 8)) & 0xFF;
                bitCount++;
            }
            if ((i + 1) % 7 == 0) {
                if (bitCount % 2 == 0) {
                    result[7 - resultIx / 8] |= 1;
                }
                resultIx++;
                bitCount = 0;
            }
            resultIx++;
        }
        return result;
    }
}
