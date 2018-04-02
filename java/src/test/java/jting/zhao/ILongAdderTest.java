package jting.zhao;

import jting.zhao.java.util.concurrent.IThreadFactory;
import jting.zhao.java.util.concurrent.IThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName: ILongAdderTest
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/15 23:41
 * Inc.All rights reserved.
 */
public class ILongAdderTest extends ITest{

    static IThreadPoolExecutor pool = new IThreadPoolExecutor(5000, 5500,
            100, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10), new IThreadFactory(), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("rejectedExecution ...");
        }
    });

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        for(int i = 0; i < 5000;i++) {
            pool.execute(new Task1(adder));
        }

        long l = adder.longValue();
        System.out.println(l);
    }

    static class Task1 implements Runnable{

        private LongAdder adder;

        public Task1(LongAdder adder) {
            this.adder = adder;
        }

        @Override
        public void run() {
            threadSleep(1);
            adder.increment();
        }
    }
}
