package com.jared.core.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-6-25
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class StringFormat {
    private static final Logger log = LoggerFactory.getLogger(StringFormat.class);

    /**
     *
     %[argument_index$][flags][width][.precision]conversion
     可选的 argument_index 是一个十进制整数，用于表明参数在参数列表中的位置。第一个参数由 "1$" 引用，第二个参数由 "2$" 引用，依此类推。
     可选的 flags 是修改输出格式的字符集。有效标志的集合取决于转换类型。
     可选 width 是一个非负十进制整数，表明要向输出中写入的最少字符数。
     可选 precision 是一个非负十进制整数，通常用来限制字符数。特定行为取决于转换类型。
     所需的 conversion 是一个表明应该如何格式化参数的字符。给定参数的有效转换集合取决于参数的数据类型。
     */
    //%[index$][标识][最小宽度]转换方式

    /**
     * 标识：
     '-'    在最小宽度内左对齐，不可以与“用0填充”同时使用
     '#'    只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
     '+'    结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
     '  '    正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
     '0'    结果将用零来填充
     ','    只适用于10进制，每3位数字之间用“，”分隔
     '('    若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+’具有同样的限制）
     */
    @Test
    public void testFormat(){
        String s1 = String.format("this is %s","hello");
        log.info(s1);
        String s2 = String.format("this is %d",2);
        log.info(s2);
        String s3 = String.format("this is %d %s",2,"hello");
        log.info(s3);
        String s4 = String.format("%,d",100000);
        log.info(s4);
        String s5 = String.format("%1$6d",-20);
        log.info("sss {},,,{}","heell",1111);
    }
}
