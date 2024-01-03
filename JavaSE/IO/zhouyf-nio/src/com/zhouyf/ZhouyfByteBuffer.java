package com.zhouyf;

import java.nio.ByteBuffer;

public class ZhouyfByteBuffer {
    public static void main(String[] args) {
        String msg = "hello, world";
        ByteBuffer buf = ByteBuffer.allocate(msg.length());
        buf.put(msg.getBytes());
        buf.flip();
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
    }
}
