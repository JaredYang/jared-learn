package com.jared.core.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;


public class AesCryptUtil {
    private static final Log logger = LogFactory.getLog(AesCryptUtil.class);
    private final static byte[] iv =
            new byte[]{0x00, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf};
    private final static byte DEFAULT_MAX_CONFOUND_SIZE = 3;

    private AesCryptUtil() {
    }

    /**
     * 对 plainData 进行加密处理，plainData会用utf-8进行编码处理。
     * 返回结果会包含数字、大小写字母以及'*','-','_'。
     * 该方法加密的结果需要用 decrypt(String cipherData, String password)解密。
     *
     * @param plainData 待加密数据
     * @param password  加密用的密码
     * @return 加密后得到的密文
     */
    public static String encrypt(String plainData, String password) {
        return encrypt(plainData, password, false);
    }

    /**
     * 该方法用来解密encrypt(String plainData, String password)生成的密文。
     * 密文的内容只能包含数字、大小写字母以及'*','-','_'。
     * 解密出来的结果会用utf-8编码生成字符串。
     *
     * @param cipherData 待解密的密文
     * @param password   解密用到的密码
     * @return 解密后得到的明文。
     */
    public static String decrypt(String cipherData, String password) {
        if (cipherData == null || !cipherData.matches("[0-9a-zA-Z*_-]+")) {
            return null;
        }
        return decrypt(cipherData, password, false);
    }

    /**
     * 对 plainData 进行加密处理，plainData会用utf-8进行编码处理。
     * 返回结果为16进制字符组成的字符串。
     * 该方法加密的结果需要用 decryptWithCaseInsensitive(String cipherData, String password)解密。
     *
     * @param plainData 待加密数据
     * @param password  加密用的密码
     * @return 加密后得到的密文
     */
    public static String encryptWithCaseInsensitive(String plainData, String password) {
        return encrypt(plainData, password, true);
    }

    /**
     * 该方法用来解密encryptWithCaseInsensitive(String plainData, String password)生成的密文。
     * 密文的内容为16进制字符组成的字符串。
     * 解密出来的结果会用utf-8编码生成字符串。
     *
     * @param cipherData 待解密的密文
     * @param password   解密用到的密码
     * @return 解密后得到的明文。
     */
    public static String decryptWithCaseInsensitive(String cipherData, String password) {
        if (cipherData == null || !cipherData.matches("[0-9a-fA-F]+")) {
            return null;
        }
        return decrypt(cipherData, password, true);
    }

    private static byte[] verifyChecksum(byte[] plainData) {
        if (plainData == null || plainData.length <= 4) {
            return null;
        }
        byte[] checksum = new byte[4];
        byte[] srcData = new byte[plainData.length - checksum.length];
        System.arraycopy(plainData, 0, srcData, 0, srcData.length);
        System.arraycopy(plainData, srcData.length, checksum, 0, checksum.length);
        return isEqualByteArray(calculateChecksum(srcData), checksum) ? srcData : null;
    }

    private static boolean isEqualByteArray(byte[] array1, byte[] array2) {
        if (array1 == null || array2 == null) {
            return array1 == null && array2 == null;
        }
        boolean isEqual = true;
        for (int i = 0; i < array1.length; i++) {
            isEqual = array1[i] == array2[i];
            if (!isEqual) {
                break;
            }

        }
        return isEqual;
    }

    /**
     * 因为加密过程中没有对明文进行checksum处理，所以该方法将会废弃。
     * 请用新的encrypt或者encryptWithCaseInsensitive方法来代替。
     *
     * @param plainData             待加密数据
     * @param password              加密密码
     * @param maxConfoundNumberSize 混淆数的长度，默认为3
     * @param caseInSensitive       放回结果是否大小写相关
     * @return 加密密文
     */
    @Deprecated
    public static String encryptWithDajieSpec(String plainData, String password, byte maxConfoundNumberSize, boolean caseInSensitive) {
        if (plainData == null) {
            return null;
        }
        byte[] cipherDataArray = null;
        try {
            Random random = new Random();
            byte[] plainDataArray = plainData.getBytes("UTF-8");
            cipherDataArray = encrypt(mixPlainData(plainDataArray, (byte) (Math.abs(random.nextInt() % maxConfoundNumberSize) + 1)), password);
        } catch (Exception e) {
            logger.debug(String.format("====>>>>PlainData is %s and password is %s", plainData, password), e);
            e.printStackTrace();
        }
        if (cipherDataArray == null || cipherDataArray.length == 0) {
            return null;
        }
        return caseInSensitive ? StringUtil.byte2HexString(cipherDataArray) : Base64.encodeWithDajieSpec(cipherDataArray);
    }

    /**
     * 因为解密过程中没有对明文进行checksum验证，所以该方法将会废弃。
     * 请用新的decrypt或者decryptWithDajieSpec方法来代替。
     *
     * @param cipherData      待解密密文
     * @param password        解密用到的密码
     * @param caseInSensitive 是否大小写相关
     * @return 解密后得到的明文。
     */
    @Deprecated
    public static String decryptWithDajieSpec(String cipherData, String password, boolean caseInSensitive) {
        if (cipherData == null) {
            return null;
        }
        byte[] cipherDataArray = caseInSensitive ? StringUtil.hexString2Bytes(cipherData) : Base64.decodeWithDajieSpec(cipherData);
        byte[] plainDataArray = decrypt(cipherDataArray, password);
        String result = null;
        if (plainDataArray != null && plainDataArray.length > 0) {
            try {
                result = new String(retrieveToBeDecryptData(plainDataArray), "UTF-8");
            } catch (Exception e) {
                logger.debug(String.format("===>>>>cipherData is %s and password is %s", cipherData, password), e);
                e.printStackTrace();
            }
        }
        return result;
    }

    private static byte[] calculateChecksum(byte[] plainData) {
        CRC32 crc32 = new CRC32();
        crc32.update(plainData);
        return toBytes(crc32.getValue());
    }

    private static byte[] toBytes(long input) {
        byte[] output = new byte[4];
        for (int i = 0; i < output.length; i++) {
            output[i] = (byte) ((input >>> (i * 8)) & 0xff);//big-endian
        }
        return output;
    }

    private static byte[] combinePlainDataWithChecksum(byte[] plainData) {
        byte[] checksum = calculateChecksum(plainData);
        byte[] combineResult = new byte[plainData.length + checksum.length];
        System.arraycopy(plainData, 0, combineResult, 0, plainData.length);
        System.arraycopy(checksum, 0, combineResult, plainData.length, checksum.length);
        return combineResult;
    }

    private static byte[] mixPlainData(byte[] plainData, byte mixDataSize) {
        if (plainData == null) {
            return plainData;
        }
        //第一位为混淆数的长度，最多mixDataSize个混淆数；紧接着下来的是混淆数；剩下的就是待加密数据
        byte[] toBeEncrypt = new byte[1 + mixDataSize + plainData.length];
        Random random = new Random();
        toBeEncrypt[0] = mixDataSize;
        for (int i = 0; i < toBeEncrypt[0]; i++) {
            toBeEncrypt[i + 1] = (byte) Math.abs(random.nextInt() % 127);
        }
        System.arraycopy(plainData, 0, toBeEncrypt, 1 + toBeEncrypt[0], plainData.length);
        return toBeEncrypt;
    }

    private static byte[] retrieveToBeDecryptData(byte[] cipherData) {
        if (cipherData == null) {
            return cipherData;
        }
        if (cipherData[0] < 0 || cipherData[0] >= cipherData.length) {
            List<Byte> list = new ArrayList<Byte>();
            for (byte aCipherData : cipherData) {
                list.add(aCipherData);
            }
            throw new IllegalArgumentException("Illegal confound size,maybe there is an error when decrypt.The decrypted data is " + StringUtil.join(list, ","));
        }
        byte[] toBeDecryptData = new byte[cipherData.length - cipherData[0] - 1];
        System.arraycopy(cipherData, 1 + cipherData[0], toBeDecryptData, 0, toBeDecryptData.length);
        return toBeDecryptData;
    }

    private static byte[] toKey(String toBeHashedString) {
        try {
            MessageDigest digestAlgorithm = MessageDigest.getInstance("MD5");
            digestAlgorithm.reset();
            digestAlgorithm.update(toBeHashedString.getBytes());
            return digestAlgorithm.digest();
        } catch (Exception e) {
            logger.warn(e);
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] aesHandler(byte[] toBeHandleData, String password, boolean isEncrypt) {
        try {
            SecretKeySpec key = new SecretKeySpec(toKey(password), "AES");
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            IvParameterSpec initialParameter = new IvParameterSpec(iv);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key, initialParameter);// 初始化
            return cipher.doFinal(toBeHandleData);
        } catch (Exception e) {
            logger.warn(e);
            e.printStackTrace();
            return null;
        }
    }

    private static String encrypt(String plainData, String password, boolean caseInsensitive) {
        if (plainData == null) {
            return null;
        }
        byte[] cipherDataArray = null;
        try {
            Random random = new Random();
            byte[] plainDataArray = plainData.getBytes("UTF-8");
            cipherDataArray = encrypt(combinePlainDataWithChecksum(mixPlainData(plainDataArray, (byte) (Math.abs(random.nextInt() % DEFAULT_MAX_CONFOUND_SIZE) + 1))), password);
        } catch (Exception e) {
            logger.debug(String.format("====>>>>plainData is %s and password is %s", plainData, password), e);
        }
        if (cipherDataArray == null || cipherDataArray.length == 0) {
            return null;
        }
        return caseInsensitive ? StringUtil.byte2HexString(cipherDataArray) : Base64.encodeWithDajieSpec(cipherDataArray);
    }

    private static String decrypt(String cipherData, String password, boolean caseInsensitive) {
        if (cipherData == null) {
            return null;
        }
        byte[] cipherDataArray = caseInsensitive ? StringUtil.hexString2Bytes(cipherData) : Base64.decodeWithDajieSpec(cipherData);
        byte[] plainDataArray = verifyChecksum(decrypt(cipherDataArray, password));
        if (plainDataArray == null || plainDataArray.length == 0) {
            logger.debug(String.format("=====>>>>>Verify checksum failed.The cipherData maybe has been modified.CipherData is %s, password is %s, caseInsensitive value is %s", cipherData, password, String.valueOf(caseInsensitive)));
            return null;
        }
        String result = null;
        try {
            result = new String(retrieveToBeDecryptData(plainDataArray), "UTF-8");
        } catch (Exception e) {
            logger.debug(String.format("===>>>>cipherData is %s and password is %s", cipherData, password), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密， password 如果为null，默认会赋值为空串。
     *
     * @param plainData 需要加密的内容
     * @param password  加密密码，默认为空串
     * @return 加密后的内容，如果plainData为null或者长度为0，会原样返回。
     */
    private static byte[] encrypt(byte[] plainData, String password) {
        if (plainData == null) {
            return plainData;
        }
        if (password == null) {
            password = "";
        }
        return aesHandler(plainData, password, true);
    }

    /**
     * 解密,password 如果为null，默认会赋值为空串。
     *
     * @param cipherData 需要加密的内容
     * @param password   解密密码,默认为空串
     * @return 解密后的内容, cipherData为空或者长度为0时或者解密出错时，返回null.
     */
    private static byte[] decrypt(byte[] cipherData, String password) {
        if (cipherData == null || cipherData.length == 0) {
            return cipherData;
        }
        if (password == null) {
            password = "";
        }
        return aesHandler(cipherData, password, false);
    }

    /*
    public static void main(String[] args) {
        String s = "7YngmQFvqY-e4Y8TnB8*_abcdefghiJKLMNOPQRSTUVWXYZ0123456789";
        System.out.println(s + " match [0-9a-zA-Z*_-]+: " + s.matches("[0-9a-zA-Z*_-]+"));
        String s2 = "79cf91ce9270e8658ab15920adf1638f1af8e498ceabcdefABCDEF0123456789";
        System.out.println(s2 + " match [0-9a-fA-F]+:" + s2.matches("[0-9a-fA-F]+"));
        System.out.println(decrypt(encrypt("2938922", UserConstant.INVITATION_ENCODE_KEY), UserConstant.INVITATION_ENCODE_KEY));
        System.out.println(decrypt(encrypt("我是i中国人", UserConstant.INVITATION_ENCODE_KEY), UserConstant.INVITATION_ENCODE_KEY));
        System.out.println(decryptWithCaseInsensitive(encryptWithCaseInsensitive("我在大街网", ""), ""));
        System.out.println(decryptWithCaseInsensitive(encryptWithCaseInsensitive("wo zai dajie wang", ""), ""));
        System.out.println("cipher of caseinsensitive: " + encryptWithCaseInsensitive("caseinsensitive", ""));
        System.out.println("cipher of casesensitive: " + encrypt("caseinsensitive", ""));
        String cipherData = encrypt("我是不是美国人", UserConstant.INVITATION_ENCODE_KEY);
        StringBuffer buffer = new StringBuffer(cipherData);
        Random random = new Random();
        int i = Math.abs(random.nextInt() % buffer.length());
        String replacement = buffer.substring(i, i + 1);
        buffer.replace(i > 2 ? i - 2 : i + 2, i, replacement);
        System.out.println("cipherData:\"" + cipherData + "\" has been modified to \"" + buffer.toString() + "\",then verify checksum will failed.Decrypted value is null:" + decrypt(buffer.toString(), UserConstant.INVITATION_ENCODE_KEY));
    } */
}
