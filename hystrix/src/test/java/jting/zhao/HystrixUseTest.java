package jting.zhao;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import jting.zhao.hystrix.commands.*;
import jting.zhao.service.impl.IRpcServiceA;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.*;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class HystrixUseTest extends AppTest {

    private Logger logger = LoggerFactory.getLogger(HystrixUseTest.class);

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    //Synchronous Execution
    @Test
    public void t1(){
        String result = new CommandHelloWorld("World").execute();
        Assert.assertEquals(result, "success");
    }


    //ASynchronous Execution
    @Test
    public void t2(){
        Future<String> result = new CommandHelloWorld("World").queue();
        try {
            Assert.assertEquals(result.get(), "success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    //Reactive Execution
    @Test
    public void t3(){
        Observable<String> result = new CommandHelloWorldObserver("World").observe();

        //non-blocking
        result.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Assert.assertEquals(s, "success");
            }
        });

        result.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                Assert.assertEquals(v, "success");
            }

        });
    }

    //Fallback
    @Test
    public void t4(){
        String result = new CommandHelloFailure("fallback").execute();
        Assert.assertEquals(result, "fallback");
    }


    //Request Collapsing请求合并
    @Test
    public void t5() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            QueryCommandCollapser q1 = new QueryCommandCollapser("QueryCommandCollapser", "1");
            QueryCommandCollapser q2 = new QueryCommandCollapser("QueryCommandCollapser-2", "2");
            QueryCommandCollapser q3 = new QueryCommandCollapser("QueryCommandCollapser", "3");
            QueryCommandCollapser q4 = new QueryCommandCollapser("QueryCommandCollapser-2", "4");
            QueryCommandCollapser q5 = new QueryCommandCollapser("QueryCommandCollapser", "5");

            Future<String> f1 = q1.queue();
            Future<String> f2 = q2.queue();
            Future<String> f3 = q3.queue();

//            threadSleep(200);  测试时间窗口

            Future<String> f4 = q4.queue();
            Future<String> f5 = q5.queue();
//            threadSleep(200);
            logger.info("f1:" + f1.get());
        }catch (Exception e){
            logger.error("出错：", e);
        }finally {
            context.shutdown();
        }

    }


    //熔断-多线程版本
    @Test
    public void t6() {
        for(int i = 0; i < 50;i++) {

//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            TaskCallBackCommand taskCallBackCommand = new TaskCallBackCommand(new IRpcServiceA(), i);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    taskCallBackCommand.execute();
                }
            });
        }


        try {
            Thread.sleep(111111111111111111L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //熔断-单线程版
    @Test
    public void t7() {
        TaskCallBackCommand command12 = new TaskCallBackCommand(new IRpcServiceA(), 12);
        TaskCallBackCommand command24 = new TaskCallBackCommand(new IRpcServiceA(), 24);
        TaskCallBackCommand command1 = new TaskCallBackCommand(new IRpcServiceA(), 1);
        TaskCallBackCommand command2 = new TaskCallBackCommand(new IRpcServiceA(), 2);
        TaskCallBackCommand command3 = new TaskCallBackCommand(new IRpcServiceA(), 3);
        TaskCallBackCommand command4 = new TaskCallBackCommand(new IRpcServiceA(), 4);
        TaskCallBackCommand command5 = new TaskCallBackCommand(new IRpcServiceA(), 5);
        TaskCallBackCommand command6 = new TaskCallBackCommand(new IRpcServiceA(), 6);
        TaskCallBackCommand command7 = new TaskCallBackCommand(new IRpcServiceA(), 7);
        TaskCallBackCommand command8 = new TaskCallBackCommand(new IRpcServiceA(), 8);
        TaskCallBackCommand command9 = new TaskCallBackCommand(new IRpcServiceA(), 9);

        command12.execute();
        command24.execute();
        command1.execute();
        command2.execute();
        command3.execute();
//
        sleep(100);
        sleep(100);
        sleep(100);

        command4.execute();
        command5.execute();

        sleep(100);
        command7.execute();
        command6.execute();

        command8.execute();
        command9.execute();
    }

    /**
     * 自动超时机制采用HystrixTimer实现，底层使用Java ScheduledThreadPoolExecutor 定期检测命令执行时间，若超时，则在HystrixTimer-x 线程中执行fallback方法。
     * 调度线程池默认采用操作系统处理器核数作为corePoolSize配置。
     *
     * 采用线程池隔离：
     *      命令在Hystrix为自己分配的线程池中执行，如命令执行超时，则原线程被中断处理。
     * 采用信号量隔离：
     *      命令在命令调用线程中执行，没有额外线程开销，
     *      如果命令执行超时，则execute后续代码执行时间为execute开始执行时间 + command run代码块执行时间。
     *      HystrixTimer中fallback执行时间是execute开始执行时间 + 命令配置的超时时间
     */
    @Test
    public void t8(){
        /** 同步调用 */
        TaskCallBackCommand command1 = new TaskCallBackCommand(new IRpcServiceA(), 240);

        Object execute = command1.execute();

        logger.info("result : " + "do2 getFallback".equals(execute));//true
    }

    @Test
    public void t9(){
        /** 响应式调用 */
        TaskCallBackCommand command1 = new TaskCallBackCommand(new IRpcServiceA(), 240);

        Observable<Object> observe = command1.observe();

        observe.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                logger.info("observe onCompleted running !");
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info("observe onError running !", throwable);
            }

            @Override
            public void onNext(Object o) {
                //超时10s后执行
                logger.info("observe onNext running ! o:{}", o.toString());
            }
        });

        logger.info("Over !");
    }



    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
