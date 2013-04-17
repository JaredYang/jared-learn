package com.jared.core.mail;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-17
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public class EmailService {
    private static Properties prop = new Properties();
    private static JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    static {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "25000");
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setUsername("363167160");
        javaMailSender.setPassword("junde19870826!");
        javaMailSender.setJavaMailProperties(prop);
    }
    public static  void  sendTextMail(SimpleMailMessage simpleMailMessage){
        if(simpleMailMessage!=null){
            javaMailSender.send(simpleMailMessage);
        }
    }

    public static void sendHtmlMail(){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo("273155303@qq.com");
            mimeMessageHelper.setFrom("363167160@qq.com");
            mimeMessageHelper.setSubject("美图测试");
            mimeMessageHelper.setText("<html><head></head><body><h1>hello!!spring html Mail</h1></br><img src=\"cid:img1\"></img></body></html>",
                    true);
            FileSystemResource img1 = new FileSystemResource(new File("D:/百度网盘/pictrue/2000.jpg"));
            mimeMessageHelper.addInline("img1", img1);
            mimeMessageHelper.addAttachment("be.jpg",img1);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

}
