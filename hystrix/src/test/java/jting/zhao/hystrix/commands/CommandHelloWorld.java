package jting.zhao.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import jting.zhao.service.impl.IRpcServiceA;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup_1"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        new IRpcServiceA().do2();
        return "success";
    }
}
