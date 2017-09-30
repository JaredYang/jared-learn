package com.jared.core.test;


public class StringTest {
    public static void main(String[] args) {
        testStringObject();
    }

    public static void testStringObject(){
        String s1 = "123";
        String s2 = s1;
        String s3 = "123";
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));
        System.out.println(s2.equals(s3));

        String s4 = new String("123");
        String s5 = new String("123");
        System.out.println(s4.equals(s5));
        if(s4 == s5){
            System.out.println("true");
        }
        changeString(s1);
        System.out.println(s1);
    }

    public static void changeString(String s){
        s = "changed";
    }
}
