package com.jared.core.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: hui.ouyang
 * Date: 12-11-30
 * Time: 下午6:51
 */
public class ParamXssStripper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamXssStripper.class);

    private ParamXssStripper() {
    }

    private static final Set<String> FORBIDDEN_TAGS = new HashSet<String>(Arrays.asList(new String[]{
            "script", "embed", "object", "style", "meta"
    }));

    private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("([0-9a-z]+)(\\s*)=(\\s*)", REGEX_FLAGS_SI);

    private static final Pattern FORBIDDEN_ATTRIBUTES = Pattern.compile("^(on.+)$", REGEX_FLAGS_SI);

    private static final Pattern FORBIDDEN_KEYWORDS = Pattern.compile(".*(javascript|mocha|eval|vbscript|livescript|expression|url).*", REGEX_FLAGS_SI);

    private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);

    private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);

    /**
     * 过滤xss
     * <p>1. 过滤不允许的标签（直接删除整个标签，不影响标签之间的内容）</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;&lt;script&gt;alert(/xss/)&lt;/script&gt;&nbsp;&nbsp;==&gt;&gt;&nbsp;&nbsp;alert(/xss/)</p>
     * <p>2. 过滤不允许的属性名（直接删除整个标签，不影响标签之间的内容）</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;&lt;button onclick="***"&gt;ok&lt;/button&gt;&nbsp;&nbsp;==&gt;&gt;&nbsp;&nbsp;ok</p>
     * <p>3. 过滤包含不允许关键字的属性值（直接删除整个标签，不影响标签之间的内容）</p>
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;&lt;img src="javascript:alert(/xss/)" /&gt;&nbsp;&nbsp;==&gt;&gt;</p>
     *
     * @param s 待检查的字符串
     * @return 过滤xss后的字符串
     */
    public static String filter(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;  //非标签内容起始位置
        int from = -1;  //标签开始位置
        int end = -1;   //标签结束位置

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '<') {   //寻找'<'
                continue;
            }
            from = i;   //记录'<'位置
            int temp = from;    //用于在寻找'>'过程中排除"<!-- -->"的影响
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(j) != '>') {   //寻找'>'
                    continue;
                }

                if (s.substring(temp, j + 1).contains("<!--") && s.substring(temp, j + 1).contains("-->")) {
                    //区域中有匹配的"<!-- -->"继续向后寻找'>'
                    temp = j;
                    continue;
                }
                //记录'>'的位置及下次搜索开始位置
                i = j;
                end = j;
                break;
            }
            sb.append(s.substring(index, from));    //原样复制非标签内容
            if (end > from) {   //找到标签
                index = i + 1;
                sb.append(processTag(s.substring(from + 1, end)));
            } else {    //未找到标签，复制剩余内容并返回
                sb.append(s.substring(from));
                return sb.toString();
            }
        }
        sb.append(s.substring(index));  //复制剩余内容
        if (!s.equals(sb.toString())) {
            LOGGER.warn("{} ==>> {}", s, sb.toString());
        }
        return sb.toString();
    }

    private static String processTag(String s) {
        //闭合标签
        Matcher m = P_END_TAG.matcher(s);
        if (m.find()) {
            String name = m.group(1).toLowerCase();
            if (FORBIDDEN_TAGS.contains(name)) {
                return "";
            } else {
                return "<" + s + ">";
            }
        }

        //开始标签
        m = P_START_TAG.matcher(s);
        if (m.find()) {
            String name = m.group(1);
            String body = m.group(2);
            if (FORBIDDEN_TAGS.contains(name.toLowerCase())) {
                return "";
            } else {
                body = body.replaceAll("[\\r\\n\\t\0]*", "").replaceAll("/\\*.*\\*/", "").replaceAll("<!--.*-->", "");
                if (body.length() <= 0) {
                    return "<" + s + ">";
                }
                if (checkAttributes(body)) {
                    return "<" + s + ">";
                }
                return "";
            }
        }
        return "<" + s + ">";
    }

    private static boolean checkAttributes(String str) {
        int start = 0;
        while (true) {
            Attribute attr = findAttribute(str, start);
            if (attr == null) {
                return true;
            }
            start = attr.end;
            if (FORBIDDEN_ATTRIBUTES.matcher(attr.name).find() || FORBIDDEN_KEYWORDS.matcher(attr.value).find()) {
                return false;
            }
        }
    }

    /**
     * 在字符串中查找属性串(xxx=aaa xxx='aaa' xxx="aaa")
     *
     * @param str       待检查字符串
     * @param fromIndex 检查起始位置
     * @return {@link Attribute}
     */
    private static Attribute findAttribute(String str, int fromIndex) {
        Matcher m = ATTRIBUTE_PATTERN.matcher(str); //搜索属性名部分
        if (!m.find(fromIndex)) {   //未发现属性名
            return null;
        }
        //搜索属性值部分
        Attribute attribute = new Attribute(m.start(), 0, m.group(1), "");
        if (m.end() >= str.length()) {  //整串结束
            attribute.end = str.length();
            return attribute;
        }
        int start = m.end();
        int end = start + 1;
        char startChar = str.charAt(start);
        if (startChar != '\'' && startChar != '\"') {   //非引号
            for (; end < str.length(); end++) {
                char cur = str.charAt(end);
                if (cur == ' ' || cur == '　') { //结束于空格
                    break;
                }
            }
            attribute.end = end;
            attribute.value = str.substring(start, end);
            return attribute;
        }
        //引号
        for (; end < str.length(); end++) {
            char cur = str.charAt(end);
            if (cur == '\\') {  //转义字符，跳过
                end += 1;
                continue;
            }
            if (cur == startChar) { //引号匹配
                end += 1;
                break;
            }
        }
        end = end > str.length() ? str.length() : end;
        attribute.end = end;
        attribute.value = str.substring(start, end);
        return attribute;
    }

    private static class Attribute {
        int start;
        int end;
        String name;
        String value;

        Attribute(int start, int end, String name, String value) {
            this.start = start;
            this.end = end;
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Attribute");
            sb.append("{start=").append(start);
            sb.append(", end=").append(end);
            sb.append(", name='").append(name).append('\'');
            sb.append(", value='").append(value).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
