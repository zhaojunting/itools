package jting.zhao.service.impl;

import jting.zhao.service.IRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class IRpcServiceA implements IRpcService {

    private Logger logger = LoggerFactory.getLogger(IRpcServiceA.class);

    @Override
    public Object query() {
        logger.info(name() + "query running!");
        Map<String, Object> result = new HashMap<>();
        result.put("name","zhaojt");
        return result;
    }

    @Override
    public void do2() {
        logger.info(name() + "do2 running!");
    }

    @Override
    public String name() {
        return "IRpcServiceA";
    }
}
