package jting.zhao.service.impl;

import com.google.gson.Gson;
import jting.zhao.service.IRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class IRpcServiceA implements IRpcService {

    private Logger logger = LoggerFactory.getLogger(IRpcServiceA.class);

    private Map<String,String> db = new HashMap<>();

    public IRpcServiceA() {
        db.put("1", "bailongma");
        db.put("2", "tangsanzang");
        db.put("3", "sunwukong");
        db.put("4", "zhubajie");
        db.put("5", "shaheshang");
    }

    @Override
    public String query(String id) {
        logger.info(name() + " query running! ID = " + id);
        return db.get(id);
    }

    @Override
    public List<String> batchQuery(List<String> ids) {
        logger.info(name() + " batchQuery running! ids = " + new Gson().toJson(ids));
        List<String> result = new ArrayList<>();
        for(int i = 0; i < ids.size(); i++){
            result.add(query(ids.get(i)));
        }
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
