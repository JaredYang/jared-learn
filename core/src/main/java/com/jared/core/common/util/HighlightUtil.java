package com.jared.core.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * User: hui.ouyang
 * Date: 2010-1-28
 * Time: 16:48:10
 */
public class HighlightUtil {
    public static String highlight(String str, String keywords) {
        if (str == null || StringUtil.isEmpty(keywords)) {
            return str;
        }
        String[] keywordsArray = keywords.split(",");
        for (String s : keywordsArray) {
            str = str.replace(s, "<span class=\"highlight\">" + s + "</span>");
        }
        return str;
    }

    public static String highlight(String str, String keywords, int length) {
        return highlight(str, keywords, "<span class=\"highlight\">%s</span>", length);
    }

    public static String highlight(String str, String keywords, String highlightPattern, int length) {
        if (str == null) {
            return str;
        }
        if (StringUtil.isEmpty(keywords) || highlightPattern == null) {
            return str.length() > length ? str.substring(0, length) + "..." : str;
        }
        keywords = keywords.replace(".", "").replace("+", "");
        Pattern p;
        try {
            p = Pattern.compile("(" + keywords.replace(",", "|") + ")", Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException pse) {
            p = Pattern.compile("(" + Pattern.quote(keywords) + ")", Pattern.CASE_INSENSITIVE);
        }
        if (p == null) {
            return str.length() > length ? str.substring(0, length) + "..." : str;
        }
        Matcher m = p.matcher(str);
        List<int[]> matches = new ArrayList<int[]>();
        while (m.find()) {
            int[] pos = new int[2];
            pos[0] = m.start();
            pos[1] = m.end();
            matches.add(pos);
        }
        if (matches.size() == 0) {
            return str.length() > length ? str.substring(0, length) : str;
        }
        int sliceLength = 10;
        int silceSize = (length / 20);
        if (silceSize > matches.size()) {
            sliceLength = length / (matches.size() * 2);
        }
        StringBuffer sb = new StringBuffer();
        int position = 0;
        for (int i = 0; i < matches.size(); i++) {
            if (i + 1 > silceSize) {
                break;
            }
            int[] pos = matches.get(i);
            String highlightStr = highlightPattern.replace("%s", str.substring(pos[0], pos[1]));
            if (position > 0) {
                if (pos[0] - position <= sliceLength * 2) {
                    sb.append(str.substring(position, pos[0]));
                } else {
                    sb.append(str.substring(position, position + sliceLength));
                    sb.append("…").append(str.substring(pos[0] - sliceLength, pos[0]));
                }
            } else {
                if (pos[0] - position > sliceLength) {
                    sb.append("…").append(str.substring(pos[0] - sliceLength, pos[0]));
                } else {
                    sb.append(str.substring(position, pos[0]));
                }
            }
            sb.append(highlightStr);
            position = pos[1];
        }
        if (position < str.length() - sliceLength) {
            sb.append(str.substring(position, position + sliceLength)).append("…");
        } else {
            sb.append(str.substring(position));
        }
        return sb.toString();
    }
}
