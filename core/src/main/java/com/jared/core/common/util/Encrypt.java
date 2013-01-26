package com.jared.core.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLDecoder;

/**
 * User: hui.ouyang
 * Date: 2010-3-3
 * Time: 14:08:31
 */
public class Encrypt {
    /**
     * 随机密钥长度 取值 0-32;
     * 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。
     * 取值越大，密文变动规律越大，密文变化 = 16 的 CKEY_LENGTH 次方
     * 当此值为 0 时，则不产生随机密钥
     * 加密前后此值需要一致
     */
    private static int CKEY_LENGTH = 4;

    private static Log log = LogFactory.getLog(Encrypt.class);

    /**
     * <pre>
     * 解密
     * </pre>
     *
     * @param code 加密后的字符串
     * @param key  私钥字符串
     * @return
     */
    public static String decode(String code, String key) {
        if (code == null || code.length() == 0 || "deleted".equals(code) || "null".equals(code)) {
            return "";
        }
        try {
            if (code.indexOf('%') > -1) {
                code = URLDecoder.decode(code, "UTF-8");
            }
            return _decode(code, key);
        } catch (Exception ignored) {}
        return "";
    }

    public static String encodeWithInsensitive(String code, String key) throws Exception {
        String encode = encode(code, key);
        byte[] encodeBytes = encode.getBytes("UTF-8");
        return StringUtil.byte2HexString(encodeBytes).toLowerCase();
    }

    public static String decodeWithInsensitive(String code, String key) throws Exception {
        String hexString = code.toLowerCase();
        if (hexString.length() % 2 != 0 || !hexString.matches("[0-9a-f]+")) {
            return "";
        }
        return decode(new String(StringUtil.hexString2Bytes(code), "UTF-8"),key);
    }

    /**
     * <pre>
     * 加密
     * </pre>
     *
     * @param code 待加密的字符串
     * @param key  私钥字符串
     * @return
     */
    public static String encode(String code, String key) {
        try {
            return _encode(code, key, 0);
        } catch (Exception e) {
            log.error("code encode error:" + code, e);
        }
        return "";
    }

    /**
     * <pre>
     * 加密，带过期时间
     * </pre>
     *
     * @param code   待加密的字符串
     * @param key    私钥字符串
     * @param expiry 过期时间，单位秒。过期时间是在生成此加密串时的当前时间
     *               解密时需要再次获取系统时间进行比较
     * @return 加密后的字符串
     */
    public static String encode(String code, String key, int expiry) {
        try {
            return _encode(code, key, expiry);
        } catch (Exception e) {
            log.error("code encode error:" + code, e);
        }
        return "";
    }

    private static String _decode(String code, String key) throws Exception {//, $expiry = 0
        if (key == null) {
            return "";
        }
        key = StringUtil.md5(key);
        String keya = StringUtil.md5(key.substring(0, 16));
        String keyb = StringUtil.md5(key.substring(16).substring(0, 16));
        String keyc;
        if (CKEY_LENGTH > 0) {
            keyc = code.substring(0, CKEY_LENGTH);
        } else {
            keyc = "";
        }

        String cryptkey = keya + StringUtil.md5(keya + keyc);

        int key_length = cryptkey.length();

        byte[] codebyte;
        int codebytelen;

        codebyte = Base64.decode(code.substring(CKEY_LENGTH));
        codebytelen = codebyte.length;


        String result = "";
        int[] box = new int[256];
        for (int i = 0; i < box.length; i++) {
            box[i] = i;
        }
        int[] rndkey = new int[256];
        for (int i = 0; i <= 255; i++) {
            rndkey[i] = (int) (cryptkey.charAt(i % key_length));
        }
        int j = 0;
        int k;
        for (int i = 0; i < 256; i++) {
            j = (j + box[i] + rndkey[i]) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        j = 0;
        k = 0;
        for (int i = 0; i < codebytelen; i++) {
            k = (k + 1) % 256;
            j = (j + box[k]) % 256;
            int tmp = box[k];
            box[k] = box[j];
            box[j] = tmp;
            result += (char) ((codebyte[i] & 0xff) ^ (box[(box[k] + box[j]) % 256]));
        }
        int timeCode = Integer.parseInt(result.substring(0, 10));
        String authCode1 = result.substring(10, 26);
        String authCode2 = StringUtil.md5(result.substring(26) + keyb).substring(0, 16);
        if ((timeCode == 0 || (timeCode - (System.currentTimeMillis() / 1000)) > 0) && authCode1.equals(authCode2)) {

            return result.substring(26);
        } else {

            return "";
        }

    }

    /*
     * 加密算法
     */

    private static String _encode(String code, String key, int expiry) throws Exception {
        if (key == null) {
            return "";
        }
        key = StringUtil.md5(key);
        String keya = StringUtil.md5(key.substring(0, 16));
        String keyb = StringUtil.md5(key.substring(16).substring(0, 16));
        String keyc;
        if (CKEY_LENGTH > 0) {
            keyc = StringUtil.md5(String.valueOf(System.currentTimeMillis()));
            keyc = keyc.substring(keyc.length() - CKEY_LENGTH);
        } else {
            keyc = "";
        }

        String cryptkey = keya + StringUtil.md5(keya + keyc);

        int key_length = cryptkey.length();

        String codebyte;
        int codebytelen;

        String time = "0000000000";
        if (expiry > 0) {
            time = String.valueOf(System.currentTimeMillis() / 1000 + expiry);
        }

        codebyte = time + StringUtil.md5(code + keyb).substring(0, 16) + code;
        codebytelen = codebyte.length();


        int[] box = new int[256];
        for (int i = 0; i < box.length; i++) {
            box[i] = i;
        }
        int[] rndkey = new int[256];
        for (int i = 0; i <= 255; i++) {
            rndkey[i] = (int) (cryptkey.charAt(i % key_length));
        }
        int j = 0;
        int k;
        for (int i = 0; i < 256; i++) {
            j = (j + box[i] + rndkey[i]) % 256;
            int tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }

        j = 0;
        k = 0;
        byte[] result = new byte[codebytelen];
        for (int i = 0; i < codebytelen; i++) {
            k = (k + 1) % 256;
            j = (j + box[k]) % 256;
            int tmp = box[k];
            box[k] = box[j];
            box[j] = tmp;
            result[i] = (byte) (((int) codebyte.charAt(i)) ^ (box[(box[k] + box[j]) % 256]));
        }

        return keyc + Base64.encode(result).replaceAll("=", "");
    }

}
