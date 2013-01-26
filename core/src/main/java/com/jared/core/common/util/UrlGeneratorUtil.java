package com.jared.core.common.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: yanshuang.liu
 * Date: 8/22/11
 * Time: 3:40 PM
 */
public class UrlGeneratorUtil {
    private static final String EMPTY_STRING = "";

    /**
     * 根据base url和参数生成完整的url
     *
     * @param base   不含参数的url地址
     * @param params url参数
     * @return 完整的url
     */
    public static String generateUrl(String base, Map<String, String> params) {
        if (StringUtil.isEmpty(base)) {
            return EMPTY_STRING;
        }

        if (params == null || params.isEmpty()) {
            return base;
        }

        List<String> p = new LinkedList<String>();
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (StringUtil.isEmpty(e.getKey())) {
                continue;
            }

            String key = UrlEncoderUtil.encodeByUtf8(e.getKey());
            if (StringUtil.isEmpty(key)) {
                continue;
            }

            if (!StringUtil.isEmpty(e.getValue())) {
                String value = UrlEncoderUtil.encodeByUtf8(e.getValue());
                if (!StringUtil.isEmpty(value)) {
                    p.add(String.format("%s=%s", key, value));
                    continue;
                }
            }

            // 如果运行到这里，证明只有key，没有value
            p.add(key);
        }

        if (p.isEmpty()) {
            return base;
        } else {
            String format = "%s?%s";
            if (base.indexOf('?') >= 0) {
                if (base.endsWith("&")) {
                    format = "%s%s";
                } else {
                    format = "%s&%s";
                }
            }
            return String.format(format, base, StringUtil.join(p, "&"));
        }
    }
}
