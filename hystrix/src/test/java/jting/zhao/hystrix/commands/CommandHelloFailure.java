package jting.zhao.hystrix.commands;

import com.google.gson.Gson;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import jting.zhao.service.impl.IRpcServiceA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaojunting1 on 2018/5/24
 */
public class CommandHelloFailure extends HystrixCommand<String> {

    private Logger logger = LoggerFactory.getLogger(CommandHelloFailure.class);

    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        Object result = new IRpcServiceA().query();
        logger.info("get result = " + new Gson().toJson(result));
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        logger.error("getFallback running !");
        return "fallback";
    }
}
