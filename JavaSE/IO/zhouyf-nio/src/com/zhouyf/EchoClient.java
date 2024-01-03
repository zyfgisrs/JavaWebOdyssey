package com.zhouyf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class EchoClient {
    public static void main(String[] args) throws IOException {
        // 连接到服务器
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 6543);
        SocketChannel client = SocketChannel.open(hostAddress);
        System.out.println("Client... started");

        // 创建缓冲区并准备消息
        ByteBuffer buffer = ByteBuffer.allocate(256);

        while (true) {
            String message = "hello, world";

            buffer.put(message.getBytes());
            buffer.flip();

            // 发送消息到服务器
            client.write(buffer);

            // 清空缓冲区
            buffer.clear();

            // 读取服务器回显的消息
            int i = client.read(buffer);
            String response = new String(buffer.array()).trim();
            System.out.println("Server response: " + response);

            // 清空缓冲区
            buffer.clear();

        }

    }
}
