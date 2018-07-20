package jting.zhao;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import jting.zhao.hystrix.commands.CommandHelloFailure;
import jting.zhao.hystrix.commands.CommandHelloWorld;
import jting.zhao.hystrix.commands.CommandHelloWorldObserver;
import jting.zhao.hystrix.commands.QueryCommandCollapser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class HystrixUseTest extends AppTest {

    private Logger logger = LoggerFactory.getLogger(HystrixUseTest.class);

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



}
