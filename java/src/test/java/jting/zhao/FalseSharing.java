package jting.zhao;

/**
 * @ClassName: FalseSharing
 * @Description: 测试缓存行，伪共享问题 https://zhuanlan.zhihu.com/p/22755195?utm_medium=social&utm_source=wechat_session
 * @Author: zhaojt
 * @Date: 2018/3/22 11:20
 * Inc.All rights reserved.
 *
 * 多线程各自写数组中某一索引位置的数据
 * 若没有padding，数组中的元素会被放入一个缓存行block中
 * 当某一线程对数组中某一元素进行写操作的时候，会使其他cpu中的整个block失效。
 * 其实失效的仅仅是当前索引位置上的元素。现象就是缓存失效，缓存击穿。
 * 解决方法就是前后加padding。保证缓存行中的数据不会互相影响。
 */
public class FalseSharing implements Runnable
{
    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = ITERATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong
    {
        public volatile long pp1, pp2, pp3, pp4, pp5, pp6, pp7;
        public volatile long value = 0L;
        public volatile long p1, p2, p3, p4, p5, p6, p7; //注释掉这行代码即为没有对齐的情况
        //22669040459
        //20097967725
        //19201877173
        // 6047432259
        // 6235909344
        // 6084591736
    }
}