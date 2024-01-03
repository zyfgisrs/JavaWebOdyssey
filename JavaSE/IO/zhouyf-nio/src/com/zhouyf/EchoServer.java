package com.zhouyf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

    public static void main(String[] args) throws Exception {
        //初始化选择器，打开一个选择器，用于监控通道的IO时间
        Selector selector = Selector.open();

        //创建夫服务器套接字并配置
        ServerSocketChannel serverSocket = ServerSocketChannel.open();//打开服务器套接字通道
        InetSocketAddress address = new InetSocketAddress("localhost", 65431);//指定服务器和端口号
        serverSocket.bind(address);//将套接字通道绑定到这个地址
        serverSocket.configureBlocking(false);//将通道设置为非阻塞模式
        // 注册通道到选择器并指定感兴趣的事件
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        //服务器主循环
        while (true) {
            //等待注册到该选择器的通道变得可用
            selector.select();

            //获取所有已准备就绪的事件的键集合，并通过迭代器遍历它们
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                //如果键对应的通道准备好接受新的客户端连接，则调用register方法。
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                //如果键对应的通道有数据可读，则调用echo方法。
                if (key.isReadable()) {
                    echo(key);
                }
                iterator.remove();
            }
        }

    }


    private static void register(Selector selector, ServerSocketChannel serverSocket) throws Exception {
        SocketChannel client = serverSocket.accept();//接受新的客户端连接。
        client.configureBlocking(false);//将新的客户端通道设置为非阻塞模式。
        client.register(selector, SelectionKey.OP_READ);//将客户端通道注册到选择器上，并对读操作感兴趣。
    }

    private static void echo(SelectionKey key) throws IOException {
        System.out.println("111111");
        SocketChannel client = (SocketChannel) key.channel();//从键中获取与之关联的通道
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int i = client.read(buffer);//读取数据到缓冲区
        if(i == -1){
            key.cancel(); // 取消选择键
            client.close(); // 关闭通道
            return;
        }
        if (new String(buffer.array()).trim().equals("bye")) {//检查                                                                                                                                 客户端是否发送了结束信号（"bye"），如果是，则关闭连接。
            client.close();
        } else {//如果不是结束信号，则回写（回显）数据到客户端，并清空缓冲区以供下一次读取.
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }
    }
}
