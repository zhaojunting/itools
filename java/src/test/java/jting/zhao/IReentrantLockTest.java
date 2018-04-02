package jting.zhao;

import jting.zhao.java.util.concurrent.IThreadFactory;
import jting.zhao.java.util.concurrent.IThreadPoolExecutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhaojt on 2018/1/21.
 */
public class IReentrantLockTest extends ITest{

    private static Logger LOGGER = LoggerFactory.getLogger(IReentrantLockTest.class);

    private static ReentrantLock lock = new ReentrantLock(true);

    private static IThreadPoolExecutor pool = new IThreadPoolExecutor(
            10, 10,
            1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(200),
            new IThreadFactory(),
            new RejectedExecutionHandler() {
                //队列满了 线程池不再RUNNING
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if(r instanceof IThreadPoolTest.Task){
                        LOGGER.info("rejected id = " + ((IThreadPoolTest.Task)r).getId());
                    }
                }
            }
    ).name("ReentrantLockTestPoll");



    public static void t1(){

        lock.lock();
        try {


            threadSleep(1000);
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {

        for(int i = 0; i < 3; i++){
            pool.execute(new Task(i));
        }

        pool.shutdown();
    }


    static class Task extends NumberedTask{

        public Task(int id) {
            super(id);
        }

        @Override
        public void run() {
            t1();
        }
    }
}
