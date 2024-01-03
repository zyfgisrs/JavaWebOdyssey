package com.zhouyf;

import java.nio.IntBuffer;

public class ZhouyfNIOIntBuffer {
    public static void main(String[] args) {
        IntBuffer buf = IntBuffer.allocate(10);
        System.out.println("capacity "+buf.capacity()+" limit: "+ buf.limit()+" position: "+ buf.position());
        buf.put(4);
        buf.put(new int[]{1,2,3});
        System.out.println("capacity "+buf.capacity()+" limit: "+ buf.limit()+" position: "+ buf.position());
        buf.flip(); //切换到读模式
        //flip() 方法会设置 limit 为之前的 position，并将 position 设置为 0，以便从 buffer 的开头开始读取数据。
        System.out.println("capacity "+buf.capacity()+" limit: "+ buf.limit()+" position: "+ buf.position());
        while(buf.hasRemaining()){
            System.out.print(buf.get() + " ");
        }
        System.out.println();
        System.out.println("capacity "+buf.capacity()+" limit: "+ buf.limit()+" position: "+ buf.position());
    }
}
