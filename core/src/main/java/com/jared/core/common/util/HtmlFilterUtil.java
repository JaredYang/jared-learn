package com.jared.core.common.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: HTML相关的正则表达式工具类
 * </p>
 * <p>
 * Description: 包括过滤HTML标记，转换HTML标记，替换特定HTML标记
 * </p>
 * User: haluo
 * Date: 2010-3-9
 * Time: 20:24:55
 */
public class HtmlFilterUtil {
    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>"; // 找出IMG标签

    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

    private HtmlFilterUtil() {
    }

    /**
     * 替换文本中的HTML标记以正常显示
     *
     * @param html 文本内容，如果含有html标签，将会被替换
     * @return 被处理后的文本内容
     */
    public static String escapeHTMLTag(String html) {
        if (!hasHTMLTag(html)) {
            return html;
        }
        StringBuilder filtered = new StringBuilder(html.length());
        char c;
        for (int i = 0; i <= html.length() - 1; i++) {
            c = html.charAt(i);
            switch (c) {
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case '"':
                    filtered.append("&quot;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                default:
                    filtered.append(c);
            }

        }
        return filtered.toString();
    }

    /**
     * 判断文本内容中HTML标记是否存在
     *
     * @param html 文本内容
     * @return 如果 文本内容中含有HTML标记，则返回true；否则返回false
     */
    public static boolean hasHTMLTag(String html) {
        boolean flag = false;
        if ((html != null) && (html.length() > 0)) {
            char c;
            for (int i = 0; i <= html.length() - 1; i++) {
                c = html.charAt(i);
                switch (c) {
                    case '>':
                        flag = true;
                        break;
                    case '<':
                        flag = true;
                        break;
                    case '"':
                        flag = true;
                        break;
                    case '&':
                        flag = true;
                        break;
                }
                if (flag) {
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 请用escapeHTMLTag替换该方法
     * 基本功能：替换文本中的HTML标记以正常显示
     *
     * @param input 文本内容，如果含有html标签，将会被替换
     * @return 被处理后的文本内容
     */
    @Deprecated
    public static String replaceTag(String input) {
        return escapeHTMLTag(input);
    }

    /**
     * 请用hasHTMLTag替换该方法
     * 判断文本内容中HTML标记是否存在
     *
     * @param input 文本内容
     * @return 如果 文本内容中含有HTML标记，则返回true；否则返回false
     */
    @Deprecated
    public static boolean hasSpecialChars(String input) {
        return hasHTMLTag(input);
    }

    /**
     * 过滤所有以"<"开头以">"结尾的标签
     *
     * @param html 文本内容
     * @return 被替换了HTML标签的文本内容
     */
    public static String filterAllHtmlTag(String html) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 过滤指定的HTML标签
     *
     * @param html        文本内容
     * @param htmlTagName 指定标签
     * @return 被替换了指定HTML标签的文本内容
     */
    public static String filterSpecialHtmlTag(String html, String htmlTagName) {
        String regxp = "<\\s*" + htmlTagName + "\\s+([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 替换指定的标签,如替换img标签的src属性值为[img]属性值[/img]
     *
     * @param html      文本内容
     * @param beforeTag 要替换的标签
     * @param tagAttrib 要替换的标签属性值
     * @param startTag  新标签开始标记
     * @param endTag    新标签结束标记
     * @return 被替换了的文本内容
     */
    public static String replaceSpecialHtmlTag(String html, String beforeTag,
                                               String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
        Matcher matcherForTag = patternForTag.matcher(html);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer();
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
                    .group(1));
            if (matcherForAttrib.find()) {
                matcherForAttrib.appendReplacement(sbreplace, startTag
                        + matcherForAttrib.group(1) + endTag);
            }
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    /**
     * http header name 不允许出现的字符
     */
    private static final char[] HEADER_NAME_SPECIAL_CHARS = new char[]{'(', ')',
            '<', '>', '@', ',', ';', ':', '\\', '\"', '/', '[', ']', '?', '=',
            '{', '}'};

    static {
        Arrays.sort(HEADER_NAME_SPECIAL_CHARS);
    }

    public static String filterHeaderName(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c > 32 && c < 127
                    && Arrays.binarySearch(HEADER_NAME_SPECIAL_CHARS, c) < 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String filterHeaderValue(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c >= 32 && c < 127) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
