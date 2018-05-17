package jting.zhao.guava;

import com.google.common.util.concurrent.RateLimiter;
import jting.zhao.ITest;
import jting.zhao.java.util.concurrent.IThreadFactory;
import jting.zhao.java.util.concurrent.IThreadPoolExecutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaojunting1 on 2018/5/8
 *
 * https://segmentfault.com/a/1190000012875897  源码
 */
public class RateLimiterTest extends ITest {
    private static Logger LOGGER = LoggerFactory.getLogger(RateLimiterTest.class);

    private static IThreadPoolExecutor pool = new IThreadPoolExecutor(
            20, 20,
            1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(5),
            new IThreadFactory(),new RejectedExecutionHandler() {

        //队列满了 线程池不再RUNNING
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if(r instanceof NumberedTask){
                LOGGER.info("rejected id = " + ((NumberedTask)r).getId());
            }
        }
    }
    ).name("RateLimiterTest-1");

    @Test
    public void t1(){
        RateLimiter limiter = RateLimiter.create(10);

        threadSleep(1000);

        for(int i = 0; i < 100; i++){
            Task1 task1 = new Task1(i, limiter);
            pool.execute(task1);
        }

    }


    @Test
    public void t2() {
        RateLimiter limiter = RateLimiter.create(10);

        threadSleep(2000);
        LOGGER.info(limiter.acquire(1) + "");
        LOGGER.info(limiter.acquire(2) + "");
        LOGGER.info(limiter.acquire(3) + "");
        LOGGER.info(limiter.acquire(4) + "");
        LOGGER.info(limiter.acquire(1) + "");
        LOGGER.info(limiter.acquire(2) + "");
    }


    @Test
    public void t3() {
        RateLimiter limiter1 = RateLimiter.create(2);
        RateLimiter limiter2 = RateLimiter.create(2);

        LOGGER.info(limiter1.acquire(2) + "");
        LOGGER.info(limiter2.acquire(2) + "");
        LOGGER.info(limiter1.acquire(2) + "");
        LOGGER.info(limiter2.acquire(2) + "");
    }





    class Task1 extends NumberedTask{

        private RateLimiter limiter ;
        public Task1(int id, RateLimiter limiter) {
            super(id);
            this.limiter = limiter;
        }

        @Override
        public void run() {
            LOGGER.info("ID = " + getId() + " try acquire ");
            limiter.acquire(getId());
            LOGGER.info(getId() + " running ... ");
        }
    }

}
