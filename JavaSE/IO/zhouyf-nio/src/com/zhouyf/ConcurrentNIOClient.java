package com.zhouyf;

import java.util.concurrent.TimeUnit;

public class ConcurrentNIOClient {
    public static void main(String[] args) throws InterruptedException {
        int numberOfClients = 60;
        String host = "localhost";
        int port = 65431;
        String message = "Very large amount of data from clientsjdflksjflskjdflksjdlfkjsldkfjsdlkjfsldjflsdkjflksdjfkdllll";

        // 创建并启动50个客户端线程
        for (int i = 0; i < numberOfClients; i++) {
            new Thread(new ClientThread(host, port, message + i)).start();
        }

        TimeUnit.SECONDS.sleep(2);
    }
}
