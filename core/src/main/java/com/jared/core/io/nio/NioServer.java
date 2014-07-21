package com.jared.core.io.nio;

import org.apache.ibatis.annotations.SelectKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 14-4-8
 * Time: 下午5:29
 * To change this template use File | Settings | File Templates.
 */
public class NioServer {

    private static Logger logger = LoggerFactory.getLogger(NioServer.class);
    /*标志数字*/
    private static int flag = 0;
    /*定义缓冲区大小*/
    private static int block = 4096;
    /*接收缓冲区*/
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(block);
    /*发送缓冲区*/
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(block);
    private Selector selector;
    private static final int TIMEOUT  = 300;

     public  NioServer(int port){
         try {
             selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             serverSocketChannel.configureBlocking(false);
             ServerSocket serverSocket =  serverSocketChannel.socket();
             serverSocket.bind(new InetSocketAddress(port));
             serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
             System.out.println("Nio Server is starting,listen on the port" + port);

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public void listen(){
        while (true){
            try {
                //监控所有注册的channel ,当其中有注册的IO 操作可以进行时，改函数返回
                //并将对应的selectionKey 加入到 selectionKey set 中
                if(selector.select(TIMEOUT) == 0){
                    System.out.print(".");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("the selectionKeys set's size is " + selectionKeys.size());
                Iterator<SelectionKey> keyIterator =  selector.selectedKeys().iterator();
                while(keyIterator.hasNext()){
                    SelectionKey selectionKey = keyIterator.next();
                    handleKey(selectionKey);
                    keyIterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleKey(SelectionKey selectionKey) throws IOException {

        ServerSocketChannel serverSocketChannel = null;
        SocketChannel  socketChannel = null;
        String receiveText;
        String sendText;
        int count;
        //测试此键的通道是否准备好接受新的套接字连接
        if(selectionKey.isAcceptable()){
            System.out.println("selectionKey is acceptable..........");
            serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
            //接受客户端建立连接的请求，并返回 SocketChannel 对象
            socketChannel = serverSocketChannel.accept();
            //配置为非阻塞
            socketChannel.configureBlocking(false);
            //注册到selector
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else if(selectionKey.isReadable()){
            System.out.println("selectionKey is readable..........");
            //返回为之创建此键的通道
            socketChannel = (SocketChannel)selectionKey.channel();
            receiveBuffer.clear();
            count = socketChannel.read(receiveBuffer);
            if(count > 0){
                receiveText = new String(receiveBuffer.array(),0,count);
                System.out.println("服务器端接受到的数据 >>> " + receiveText);
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            }
        }else if(selectionKey.isWritable()){
            System.out.println("selectionKey is writable..........");
            sendBuffer.clear();
            socketChannel = (SocketChannel)selectionKey.channel();
            sendText = "message from server--" + flag++;
            sendBuffer.put(sendText.getBytes());
            sendBuffer.flip();
            socketChannel.write(sendBuffer);
            //System.out.println("服务器端向客户端发送数据 >>> " + sendText);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

    }

    public static void main(String[] args) throws IOException {
        int port = 8888;
        NioServer server = new NioServer(port);
        server.listen();
    }

}
