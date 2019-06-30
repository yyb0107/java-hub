package com.bingo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Bingo
 * @title: NioClient
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  17:27
 */
public class NioClient {

    static Selector selector;
    static ByteBuffer buffer = ByteBuffer.allocate(1024);
    static boolean exit = false;

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while(!exit){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();

                process(key);
            }
        }

    }

    private static void process(SelectionKey key) throws IOException {
        if (key.isConnectable()){
            System.out.println("开始连接");
            SocketChannel sc = (SocketChannel)key.channel();
//            sc.connect(new InetSocketAddress("127.0.0.1", 8080));
            if (sc.isConnectionPending()) {
                sc.finishConnect();
                System.out.println("connect success !");
            }
            sc.register(selector, SelectionKey.OP_WRITE);
        }else if(key.isReadable()){
            System.out.println("开始读");
            SocketChannel sc = (SocketChannel)key.channel();
            int len = -1;
            StringBuffer sb = new StringBuffer();
            while((len=sc.read(buffer))!=-1){
                sb.append(new String(buffer.array(),0,len));
                buffer.flip();
            }
            System.out.println("client receive data :" +sb.toString());
            exit= true;
            sc.close();
        }else if(key.isWritable()){
            System.out.println("开始写");
            SocketChannel sc = (SocketChannel)key.channel();
            sc.write(ByteBuffer.wrap(new String("客户端向服务端发送请求数据").getBytes()));
            sc.register(selector, SelectionKey.OP_READ);
            System.out.println("写完毕");
        }
    }
}
