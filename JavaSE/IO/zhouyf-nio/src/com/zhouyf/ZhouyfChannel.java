package com.zhouyf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ZhouyfChannel {
    public static void main(String[] args) {
        String sourceFile = "data.txt";
        String destinationFile = "copy.txt";
        try (
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
                FileChannel outChannel = fileOutputStream.getChannel();
                FileChannel inChannel = fileInputStream.getChannel()) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (true) {
                buf.clear();

                int bytesRead = inChannel.read(buf);

                if (bytesRead == -1) {
                    break;
                }

                buf.flip();
                outChannel.write(buf);
            }
            System.out.println("文件复制完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
