package com.zhouyf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        int port = 65431;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("BIOServer has started, listening on port: " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();  // 阻塞直到接收到新连接
            System.out.println(System.currentTimeMillis());
            System.out.println("Connection from " + clientSocket);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    if ("bye".equals(message.toLowerCase())) {
                        break;
                    }
                    out.println("Echo: " + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
