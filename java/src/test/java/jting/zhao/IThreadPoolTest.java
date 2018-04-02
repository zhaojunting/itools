package jting.zhao;

import jting.zhao.java.util.concurrent.IThreadFactory;
import jting.zhao.java.util.concurrent.IThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by zhaojt on 2018/1/20.
 */
public class IThreadPoolTest extends ITest{

    private static Logger LOGGER = LoggerFactory.getLogger(IThreadPoolTest.class);

    private static IThreadPoolExecutor pool = new IThreadPoolExecutor(
            5, 10,
            1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(5),
            new IThreadFactory(),
            new RejectedExecutionHandler() {

                //队列满了 线程池不再RUNNING
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if(r instanceof Task){
                        LOGGER.info("rejected id = " + ((Task)r).getId());
                    }
                }
            }
    ).name("TestPoll-1").setMonitorable();//核心线程回收


    /**
     * 测试线程池各个参数，验证底层运行机制
     */
    public static void test1(){
        for (int i = 0; i < 50; i++){
            pool.execute(new Task(i));
        }

        pool.shutdown();

        try {
            boolean awaitTermination = pool.awaitTermination(1, TimeUnit.SECONDS);
            LOGGER.info("awaitTermination = " + awaitTermination);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            pool.shutdownNow();
        }

        threadSleep(10000);

        //测试拒绝
        for (int i = 0; i < 5; i++){
            pool.execute(new Task(i));
        }
    }

    public void test2(){
        //初始化
        ThreadPoolExecutor pool2 = new IThreadPoolExecutor(
                0, 10,
                1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(200),
                new IThreadFactory(),
                new RejectedExecutionHandler() {

                    //队列满了 线程池不再RUNNING
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        if(r instanceof Task){
                            LOGGER.info("rejected id = " + ((Task)r).getId());
                        }
                    }
                }
        ).name("TestPoll-2").setMonitorable().allowCoreThreadTimeOut();//核心线程回收


        //执行任务
        for (int i = 0; i < 10; i++){
            pool2.execute(new Task(i));
        }

//        pool2.shutdown();
    }

    /**
     * TODO 局部变量的pool对象垃圾回收测试  不显示调用shutdown就无法回收  为啥
     */
    public static void test3(){
        pool.execute(new Task2(100));

        LOGGER.info("first gc");
        System.gc();
        threadSleep(1000 * 3);

        LOGGER.info("second gc");
        System.gc();

        threadSleep(1000 * 5);

        LOGGER.info("last gc");
        System.gc();
    }
    public static void main(String[] args) {

        test3();


    }


    static class Task extends NumberedTask{

        public Task(int id) {
            super(id);
        }

        public void run() {
            LOGGER.info("task " + getId() + " running ...");
            threadSleep(1000);
            LOGGER.info("task " + getId() + " over ...");
        }
    }

    //模拟子线程创建线程池
    static class Task2 extends NumberedTask{

        public Task2(int id) {
            super(id);
        }

        public void run() {
            LOGGER.info("task " + getId() + " running ...");
            new IThreadPoolTest().test2();
            LOGGER.info("task " + getId() + " over ...");
        }
    }


}
