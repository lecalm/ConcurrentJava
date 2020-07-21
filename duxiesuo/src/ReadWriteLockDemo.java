import com.sun.javafx.collections.MappingChange;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//资源类
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    public void put(String key, Object value){
        System.out.println(Thread.currentThread().getName() + "\t 正在写入： "+key);
        try{
            TimeUnit.MILLISECONDS.sleep(300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "\t 写入完成： ");

    }

    public void get(String key){
        System.out.println(Thread.currentThread().getName() + "\t 正在读取：");
        try{
            TimeUnit.MILLISECONDS.sleep(300);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Object res = map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读取完成： "+res);

    }
}


public class ReadWriteLockDemo {
}
