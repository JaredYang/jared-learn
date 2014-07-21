package com.jared.core.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-11-27
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class TimeClientHandler extends IoHandlerAdapter {
    private String messages;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("client session created!");
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("client session opened!");
        new Thread(){
            public void run(){
                Scanner scanner = new Scanner(System.in);
                while (true){
                    System.out.println("客户端发送：");
                    String message = scanner.nextLine();
                    session.write(message);
                }
            }


        }.start();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("client session closed!");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("client receive message:" + message.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        //System.out.println("client has send message:" + message.toString());
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
