package com.bingo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Bingo
 * @title: NioServer
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  16:56
 */
public class NioServer {

    private int port;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(8080);
        server.open();
        server.listen();
    }

    public NioServer(int port) {
        this.port = port;
    }

    public void open() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false);

        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        while(true){
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterators = keys.iterator();

            while(iterators.hasNext()){
                SelectionKey key = iterators.next();
                iterators.remove();
                process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {

        if(key.isAcceptable()){
            System.out.println("收到了请求");
            ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
            SocketChannel sc = serverChannel.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);

        }else if(key.isReadable()){
            System.out.println("开始读");
            SocketChannel sc = (SocketChannel)key.channel();
            int len = -1;
            StringBuffer sb = new StringBuffer();

            while((len = sc.read(buffer)) > 0){
                buffer.flip();
                sb.append(new String(buffer.array(),0,len));
            }
            SelectionKey k = sc.register(selector, SelectionKey.OP_WRITE);
            k.attach(sb.toString());
            System.out.println("Server端收到请求 ："+sb.toString());

        }else if(key.isWritable()){
            System.out.println("开始写");
            SocketChannel sc = (SocketChannel)key.channel();
            Object obj = key.attachment();
            sc.write(ByteBuffer.wrap(obj.toString().getBytes()));
            sc.close();
            System.out.println("Server发送数据完毕 ："+obj);

        }
    }
}
