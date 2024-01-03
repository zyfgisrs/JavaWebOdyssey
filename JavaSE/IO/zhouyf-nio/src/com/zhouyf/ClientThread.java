package com.zhouyf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientThread implements Runnable{
    private InetSocketAddress hostAddress;
    private String message;

    public ClientThread(String address, int port, String message) {
        this.hostAddress = new InetSocketAddress(address, port);
        this.message = message;
    }

    @Override
    public void run() {
        try {
            // 连接到服务器
            SocketChannel client = SocketChannel.open(hostAddress);

            // 发送消息
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            client.write(buffer);

            // 清空缓冲区以准备接收数据
            buffer.clear();

            // 接收服务器回显的数据
            client.read(buffer);
            String response = new String(buffer.array()).trim();
            System.out.println("Server response: " + response);

            // 关闭连接
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
