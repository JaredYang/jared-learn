package com.jared.core.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-11-27
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class TimeServerHandler extends IoHandlerAdapter {
    private String messages;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("server receive message:" + message.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("server session created!");
    }

    @Override
    public void sessionOpened(final IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("server session opened!");
        new Thread(){
           public void run(){
               while (true){
                   if(!"".equals(messages)&&null!=messages){
                       session.write(messages);
                       setMessages(null);
                   }
               }
           }
        }.start();

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("server session closed!");

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
