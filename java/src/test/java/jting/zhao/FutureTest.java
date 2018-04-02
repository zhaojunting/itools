package jting.zhao;

import jting.zhao.java.util.concurrent.IThreadFactory;
import jting.zhao.java.util.concurrent.IThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @ClassName: FutureTest
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/23 19:21
 * Inc.All rights reserved.
 */
public class FutureTest {


    private static Logger LOGGER = LoggerFactory.getLogger(FutureTest.class);

    private static IThreadPoolExecutor pool = new IThreadPoolExecutor(
            5, 10,
            1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(5),
            new IThreadFactory(),
            new RejectedExecutionHandler() {

                //队列满了 线程池不再RUNNING
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if(r instanceof IThreadPoolTest.Task){
                        LOGGER.info("rejected id = " + ((IThreadPoolTest.Task)r).getId());
                    }
                }
            }
    ).name("TestPoll-1").setMonitorable();//核心线程回收

    public static void main(String[] args) {
        Future<String> result = pool.submit(() -> {
            return "Success";
        });


        try {
            String s = result.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
