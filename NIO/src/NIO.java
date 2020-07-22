import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIO {
    @Test
    public void test1(){
        String str= "abcde";

        //分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-------allocate()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //利用put()存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-------put()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //切换到读取数据的模式
        buf.flip();
        System.out.println("-------read()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //开始读取数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
//        System.out.println((char)buf.get(1));
        System.out.println(new String(dst,0,dst.length));
        System.out.println("-------get()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //rewind():可以重复读取数据
        buf.rewind();
        System.out.println("-------rewind()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //clear():清空缓冲区.但是缓冲区的数据依然存在，但是处于“被遗忘状态”
        buf.clear();
        System.out.println("-------clear()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
//        System.out.println((char)buf.get(4));
    }
    @Test
    public void test2() throws Exception{
        FileInputStream fis = new FileInputStream("1.jpg");
        FileOutputStream fos = new FileOutputStream("2.jpg");

        //1、获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        //2、分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3、将通道中的数据存入缓冲区中
        while(inChannel.read(buf)!=-1){
            buf.flip(); //切换读取数据的模式
            //4、将缓冲区中的数据写入通道中
            outChannel.write(buf);
            buf.clear();//清空缓冲区
        }
        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();
    }
}
