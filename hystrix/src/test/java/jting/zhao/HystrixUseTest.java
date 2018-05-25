package jting.zhao;

import jting.zhao.hystrix.commands.CommandHelloFailure;
import jting.zhao.hystrix.commands.CommandHelloWorld;
import jting.zhao.hystrix.commands.CommandHelloWorldObserver;
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
}
