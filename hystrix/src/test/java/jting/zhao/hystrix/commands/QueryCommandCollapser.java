package jting.zhao.hystrix.commands;

import com.google.gson.Gson;
import com.netflix.hystrix.*;
import jting.zhao.service.impl.IRpcServiceA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhaojunting1 on 2018/7/18
 */
public class QueryCommandCollapser extends HystrixCollapser<List<String>,String,String> {

    private Logger logger = LoggerFactory.getLogger(QueryCommandCollapser.class);

    public QueryCommandCollapser(String collapserKey, String reqId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey(collapserKey))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(50)));
        this.reqId = reqId;
    }

    private String reqId;

    @Override
    public String getRequestArgument() {
        return reqId;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> collapsedRequests) {
        return new BatchQueryCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, String>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, String> collapsedRequest : collapsedRequests) {
            String result = batchResponse.get(count++);
            collapsedRequest.setResponse(result);
        }
    }



    class BatchQueryCommand extends HystrixCommand<List<String>>{
        private Logger logger = LoggerFactory.getLogger(BatchQueryCommand.class);

        private Collection<CollapsedRequest<String, String>> collapsedRequests;

        protected BatchQueryCommand(Collection<CollapsedRequest<String, String>> collapsedRequests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BatchQueryCommand"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("batchQuery")));
            this.collapsedRequests = collapsedRequests;
        }

        @Override
        protected List<String> run() throws Exception {

            List<String> reqList = new ArrayList<>();
            for(CollapsedRequest<String, String> collapsedRequest : collapsedRequests){
                reqList.add(collapsedRequest.getArgument());
            }
            logger.info("调 batchQuery ： " + new Gson().toJson(reqList));
            return new IRpcServiceA().batchQuery(reqList);
        }
    }


}
