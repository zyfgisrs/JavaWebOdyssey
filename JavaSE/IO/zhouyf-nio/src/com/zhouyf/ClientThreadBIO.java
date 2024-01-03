package com.zhouyf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


public class ClientThreadBIO implements Runnable{
    private InetSocketAddress hostAddress;
    private String message;

    public ClientThreadBIO(String address, int port, String message) {
        this.hostAddress = new InetSocketAddress(address, port);
        this.message = message;
    }

    @Override
    public void run() {
        try {
            // 连接到服务器
            Socket socket = new Socket(hostAddress.getAddress(), hostAddress.getPort());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 发送消息
            out.println(message);

            // 获取输入流，用于接收服务器的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 读取并打印响应
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Server response: " + response);
                break; // 假设服务器响应一行后就结束，退出循环
            }

            // 关闭资源
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
