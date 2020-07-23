package Init;

/**
 * 子类的初始化<clint>
 *     (1) j = method();
 *     (2)子类的静态代码块
 *
 *  先初始化父类：（5）（1）
 *  初始化子类： （10）（6）
 *
 *  子类的实例初始化<init>：
 *      （1）super()（最前）（指的是整个父类的实例初始化操作完成）（9）（3）（2）
 *      (2) i=test  （2）和（3）按顺序 （9）
 *      (3) 子类的非静态代码块 （8）
 *      (4) 子类的无参构造（最后） （7）
 *  因为创建了两个Son对象，因此实例化方法<init>执行了两次
 *  因此第二次实例化上述过程又执行了一遍 （9）（3）（2）（9）（8）（7）
 */
public class Son extends Father{
    private int i = test();
    private static int j = method();

    static{
        System.out.println("(6)");
    }

    Son(){
        //super() 写或不写都在 在子类构造器一定会调用父类的构造器
        System.out.println("(7)");
    }
    {
        System.out.println("(8)");
    }

    public int test(){
        System.out.println("(9)");
        return 1;
    }
    public static int method(){
        System.out.println("(10)");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }
}