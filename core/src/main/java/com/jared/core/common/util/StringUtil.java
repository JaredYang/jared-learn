package com.jared.core.common.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: hui.ouyang
 * Date: 2009-12-30
 * Time: 12:21:05
 */
public class StringUtil {
    private static final String[] hexdigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String md5(String s) {

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.reset();
            md.update(s.getBytes());
            byte[] encodedStr = md.digest();
            StringBuffer buf = new StringBuffer();

            for (byte anEncodedStr : encodedStr) {
                int n = ((int) anEncodedStr & 0xff);
                int d1 = n / 16;
                int d2 = n % 16;
                buf.append(hexdigits[d1]).append(hexdigits[d2]);
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNumeric(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        for (int i = s.length(); --i >= 0; ) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String s) {
        boolean isInteger = true;
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            isInteger = false;
        }
        return isInteger;
    }

    public static boolean isLong(String s) {
        boolean isLong = true;
        try {
            Long.parseLong(s);
        } catch (Exception e) {
            isLong = false;
        }
        return isLong;
    }

    @Deprecated
    public static int valueOf(String s) {
        if (!isNumeric(s)) {
            return 0;
        } else {
            return Integer.valueOf(s);
        }
    }

    /**
     * 空格过滤，包含全角空格和半角空格
     *
     * @param s 字符串
     * @return 过滤空格（包含全角空格）后的字符串
     */
    public static String trim(String s) {
        if (s == null) {
            return s;
        }
        return s.replaceAll("[ 　]+", "");
    }

    /**
     * 判断字符串是否为空
     *
     * @param s 字符串
     * @return true：null，""， 只包含空格（包括全角空格）
     */
    public static boolean isEmpty(String s) {
        return s == null || trim(s).length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String join(List<?> list, String delimiter) {
        if (list == null || list.size() <= 0) {
            return "";
        }
        if (delimiter == null || delimiter.isEmpty()) {
            delimiter = " ";
        }
        StringBuilder buffer = new StringBuilder();
        int count = 1;
        for (Object aList : list) {
            if (aList == null) {
                continue;
            }
            if (count != 1) {
                buffer.append(delimiter);
            }
            buffer.append(aList.toString());
            count++;
        }
        return buffer.toString();
    }

    public static String join(Object[] array, String delimiter) {
        List<Object> list;
        if (array == null || array.length == 0) {
            list = Collections.emptyList();
        } else {
            list = Arrays.asList(array);
        }
        return join(list, delimiter);
    }

    public static List<String> splitStringByDelimiter(String toBeSplit, String delimiter, String matchRegex) {
        List<String> resultList = new ArrayList<String>();
        if (toBeSplit != null && !toBeSplit.isEmpty()) {
            if (delimiter == null) {
                delimiter = "";
            }

            String[] items = toBeSplit.split(delimiter);
            if (matchRegex == null) {
                Collections.addAll(resultList, items);
            } else {
                for (String item : items) {
                    if (item.matches(matchRegex)) {
                        resultList.add(item);
                    }
                }
            }
        }
        return resultList;
    }

    public static String capitalize(String srcString) {
        if (srcString == null || srcString.isEmpty()) {
            return srcString;
        }
        String firstChar = srcString.substring(0, 1);
        return firstChar.toUpperCase() + (srcString.length() > 1 ? srcString.substring(1) : "");
    }

    public static boolean isEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return false;
        }
        Pattern p = Pattern.compile("^[_a-z0-9\\.\\-]+@([\\._a-z0-9\\-]+\\.)+[a-z0-9]{2,4}$");
        Matcher m = p.matcher(email.toLowerCase());
        return m.matches();
    }


    /**
     * js特殊字符转义
     * ' ===> \'
     * " ===> \"
     * & ===> \&
     * \ ===> \\
     * \n ===> \\n
     * \r ===> \\r
     * \t ===> \\t
     * \b ===> \\b
     * \f ===> \\f
     *
     * @param str
     * @return string
     */
    public static String jsEscape(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(str.length() + (str.length() / 2));
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (chr == '\'') {
                sb.append("\\'");
            } else if (chr == '"') {
                sb.append("\\\"");
            } else if (chr == '&') {
                sb.append("\\&");
            } else if (chr == '\\') {
                sb.append("\\\\");
            } else if (chr == '\n') {
                sb.append("\\n");
            } else if (chr == '\r') {
                sb.append("\\r");
            } else if (chr == '\t') {
                sb.append("\\t");
            } else if (chr == '\b') {
                sb.append("\\b");
            } else if (chr == '\f') {
                sb.append("\\f");
            } else {
                sb.append(chr);
            }
        }
        return sb.toString();
    }

    public static String breakword(String s, int maxlength) {
        StringBuffer sb = new StringBuffer();
        int start = -1;
        int end;
        for (int i = 0; i < s.length(); i++) {
            int ascCode = (int) s.charAt(i);
            sb.append(s.charAt(i));
            //8 9  10 13 32分别对应退格 制表 换行 回车 空格
            if (ascCode < 128 && ascCode != 8 && ascCode != 9 && ascCode != 10 && ascCode != 13 && ascCode != 32) {
                if (start == -1) {
                    start = i;
                }
                end = i;
                if (end - start == (maxlength - 1)) {
                    sb.append(" ");
                    start = -1;
                }
            }
        }
        return sb.toString();
    }

    public static byte[] hexString2Bytes(String src) {
        int length = src.length() / 2;
        byte[] result = new byte[length];
        char[] array = src.toCharArray();
        for (int i = 0; i < length; i++) {
            result[i] = uniteBytes(array[2 * i], array[2 * i + 1]);
        }
        return result;
    }

    public static String byte2HexString(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String feedHtml(String content, String wwwDomain, String assetDomain, String target) {
        if (StringUtil.isEmpty(content)) {
            return "";
        }

        if (StringUtil.isEmpty(wwwDomain)) {
            throw new IllegalArgumentException("please get value from config for domains.www");
        }
        if (StringUtil.isEmpty(assetDomain)) {
            throw new IllegalArgumentException("please get value from config for domains.assets");
        }

        content = content.replaceAll("\r\n", " &nbsp;");
        content = content.replaceAll("\n", " &nbsp;");
        content = content.replaceAll("\\/\\/([[\\u4e00-\\u9fa5]*\\w]+)\\#(\\d+)\\#", "\\/\\/<a href=\"http://" + wwwDomain + "/profile/$2\" target=\"" + target + "\">$1</a>");
        return content.replaceAll("\\[em:(\\d+):\\]", "<img src=\"http://" + assetDomain + "/images/face/$1.gif\" />");
    }

    public static String feedHtml(String content, String wwwDomain, String assetDomain) {
        return feedHtml(content, wwwDomain, assetDomain, "_self");
    }


    public static int feedLength(String content) {
        content = content.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("(^　*)|(　*$)", "").replaceAll("\\#\\d+\\#", "");
        int length = content.replaceAll("(https|http|ftp|rtsp|mms):\\/\\/[0-9a-zA-Z_\\%\\&\\?\\.\\+\\$/\\=\\-]+", "").length();
        String str = "((https|http|ftp|rtsp|mms)://[0-9a-zA-Z_\\%\\&\\?\\.\\+\\$/\\=\\-]+)";
        Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        List<String> urls = new ArrayList<String>();
        while (matcher.find()) {
            urls.add(matcher.group(0));
        }
        for (String url : urls) {
            if (url.length() > 141) {
                length = length + url.length() - 141 + 22;
            } else {
                length = length + 22;
            }
        }
        return length;
    }

    public static String feedReplace(String content) {
        content = content.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("(^　*)|(　*$)", "");
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("'", "&#39;");
        content = content.replace(" ", " &nbsp;");
        content = content.replaceAll("\r\n", " &nbsp;");
        content = content.replaceAll("\n", " &nbsp;");
        return content;
    }

    // domainFilter should be domains.root, domains.www etc
    public static String transforURL(String html, String domainFilter, String domain) {
        if (StringUtil.isEmpty(html)) {
            return html;
        }

        return html.replace("${configs['" + domainFilter + "']}", domain);
    }

    private static byte uniteBytes(char mostChar, char secondChar) {
        byte b0 = Byte.decode("0x" + String.valueOf(mostChar));
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + String.valueOf(secondChar));
        return (byte) (b0 | b1);
    }

    /**
     * 判断是否是国内手机号 ,不能做强制校验 ，不适合所有场景
     * @param mobile
     * @return boolean
     */
    public static boolean isMobileNO(String mobile){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[2,5,7]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static void main(String[] args)   {
        System.out.println(StringUtil.isMobileNO("14212345678"));
    }
}
