package com.jared.core.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-11-27
 * Time: 上午11:06
 * To change this template use File | Settings | File Templates.
 */
public class MinaClient {

    private TimeClientHandler timeClientHandler  = new TimeClientHandler();
    private IoSession session;

    public static void main(String[] args){
        MinaClient minaClient = new MinaClient();
        minaClient.buildMinaClient();
    }

    private void buildMinaClient(){
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger",new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName("UTF-8"))));
        connector.setConnectTimeoutMillis(1000);
        connector.setHandler(timeClientHandler);
        ConnectFuture connectFuture  = connector.connect(new InetSocketAddress("127.0.0.1",9123));
        connectFuture.awaitUninterruptibly();
        session = connectFuture.getSession();
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }



}
