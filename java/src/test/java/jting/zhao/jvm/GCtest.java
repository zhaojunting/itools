package jting.zhao.jvm;

/**
 * @ClassName: GCtest
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/22 18:26
 * Inc.All rights reserved.
 */
public class GCtest {

    public static int _1MB = 1024 * 1024;


    static MyThreadLocal<Object> ctx = new MyThreadLocal<Object>(allocation(5 * _1MB));

    public static void main(String[] args) throws InterruptedException {
        t2();

    }

    public static byte[] allocation(int size){
        System.out.println("allocation.......... " + size/1024 + 'K');
        byte[] b = new byte[size];
        System.out.println("allocation.......... over!");
        return b;
    }


    public static void t1(){
        byte[] a,b,c,d,e,f,g,h,i,j,k;

        a = allocation(2 * _1MB);
        b = allocation(2 * _1MB);
        c = allocation(2 * _1MB);
        d = allocation(2 * _1MB);

        e = allocation(2 * _1MB);
        f = allocation(2 * _1MB);
        g = allocation(2 * _1MB);
        h = allocation(2 * _1MB);

        i = allocation(2 * _1MB);
        j = allocation(2 * _1MB);
        k = allocation(2 * _1MB);
    }

    /**
     * 测试ThreadLocal 引起的内存泄漏问题
     */
    public static void t2() throws InterruptedException {

        //key= 5MB  value = 4MB
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] b1 = allocation(4 * _1MB);
                ctx.set(b1);

                try {
//                    Thread.sleep(100);
                    System.out.println("sleep..........");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
//        thread = null; //这句话没啥用  线程run完了 b1就可以回收了，ctx是static的 不会被 回收


        Thread.sleep(1000);
        System.out.println("sleep..........");

        //申请内存
        byte[] a,b,c,d,e,f,g,h,i,j,k;

        a = allocation(2 * _1MB);
        b = allocation(2 * _1MB);
        c = allocation(4 * _1MB);
//        d = allocation(4 * _1MB);
//
//        e = allocation(4 * _1MB);



    }

    static class MyThreadLocal<T> extends ThreadLocal<T>{

        byte[] store ;

        public MyThreadLocal(byte[] store) {
            this.store = store;
        }
    }

}
