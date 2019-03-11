package jting.zhao;

import com.google.gson.Gson;
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
            20, 40,
            1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(8000),
            new IThreadFactory(),
            new RejectedExecutionHandler() {

                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if(r instanceof NumberedTask){
                        LOGGER.info("rejected id = " + ((NumberedTask)r).getId());
                    }
                }
            }
    ).name("TestPoll-1");//核心线程回收


    /**
     * 测试线程池各个参数，验证底层运行机制
     */
    public static void test1(){
        for (int i = 0; i < 50; i++){
            pool.execute(new Task(i));
        }

//        pool.shutdown();

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

    public static void test2(){
        //初始化
        ThreadPoolExecutor pool2 = new IThreadPoolExecutor(
                10, 20,
                1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(100000),
                new IThreadFactory(),
                new RejectedExecutionHandler() {

                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        if(r instanceof Task){
                            LOGGER.info("rejected id = " + ((Task)r).getId());
                        }
                    }
                }
        ).name("TestPoll-2").setMonitorable().allowCoreThreadTimeOut();//核心线程回收


        //执行任务
        for (int i = 0; i < 10000; i++){
            pool2.execute(new Task(i));
        }

        for (int i = 0; i < 10000; i++){
            pool.execute(new Task(i));
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

//        String[] split = "123456|ABCDEF".split("\\|");
//        LOGGER.info(new Gson().toJson(split));
        test1();
    }



    static class Task extends NumberedTask{

        public Task(int id) {
            super(id);
        }

        public void run() {
            LOGGER.info("task " + getId() + " running ...");
            threadSleep(800);
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
