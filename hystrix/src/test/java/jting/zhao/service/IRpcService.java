package jting.zhao.service;

import java.util.List;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public interface IRpcService {

    String query(String id);

    List<String> batchQuery(List<String> ids);

    void do2();

    String name();
}
