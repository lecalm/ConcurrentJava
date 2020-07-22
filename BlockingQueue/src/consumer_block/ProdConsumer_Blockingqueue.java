package consumer_block;

import java.nio.channels.FileChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile/CAS/atomoicInteger/BlockQueue/线程交互/原子引用
 */
class ShareResourse{
    private volatile boolean FLAG = true; //默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger(); // 默认值是0

    //通用 适配各种阻塞队列
    BlockingQueue<String> blockingQueue = null;

    public ShareResourse(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void product() throws Exception {
        String data = null;
        boolean retValue;
        while (FLAG){
           data = atomicInteger.incrementAndGet()+ "";
           retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS); //2s后插入数据
           if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列" + data + "成功");
            }
            else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列" + data + "失败");
            }
            //通过时间的控制使得生产者线程生产之后，消费线程等待时间不到2秒的时间内消费，这样保证了生产者消费者轮流消费
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停了，表示FLAG=false,生产动作结束");
    }

    public void consumer() throws Exception {
        String result = null;
        while (FLAG){

            result = blockingQueue.poll(2L, TimeUnit.SECONDS); //2s后取出数据
            if(null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到蛋糕，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列蛋糕" + result + "成功");
        }
    }

    public void stop(){
        this.FLAG = false;
    }
}
public class ProdConsumer_Blockingqueue {
    public static void main(String[] args) throws Exception {
        ShareResourse shareResourse = new ShareResourse(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try{
                shareResourse.product();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Product").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            try{
                shareResourse.consumer();
                System.out.println();
                System.out.println();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Consumer").start();
        //暂停一会儿线程
        try{TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e){e.printStackTrace();}
        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束了");
        shareResourse.stop();
    }
}
