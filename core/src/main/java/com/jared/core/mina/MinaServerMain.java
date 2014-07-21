package com.jared.core.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-11-27
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class MinaServerMain {
    private TimeServerHandler timeServerHandler = new TimeServerHandler();
    private static String serverMessage;
    public static void main(String[] args){
        MinaServerMain minaServerMain = new MinaServerMain();
        minaServerMain.buildMinaServer();
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("服务器发送：");
            serverMessage = scanner.nextLine();
            minaServerMain.serverSendMessage(serverMessage);
        }
    }

    private void buildMinaServer(){
        try {
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            acceptor.setHandler(timeServerHandler);
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10);
            acceptor.bind(new InetSocketAddress(9123));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serverSendMessage(String message){
        timeServerHandler.setMessages(message);
    }
}
