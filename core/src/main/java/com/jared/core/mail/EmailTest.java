package com.jared.core.mail;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-17
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class EmailTest {
    public static void main(String[] args){
        EmailTest emailTest = new EmailTest();
        emailTest.sendEmailWithImage();
    }

    public void sendTextEmail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("273155303@qq.com");
        simpleMailMessage.setFrom("363167160@qq.com");
        simpleMailMessage.setSubject("test");
        simpleMailMessage.setText("testTextText");
        EmailService.sendTextMail(simpleMailMessage);
    }

    public void sendEmailWithImage(){
        EmailService.sendHtmlMail();
    }
}
