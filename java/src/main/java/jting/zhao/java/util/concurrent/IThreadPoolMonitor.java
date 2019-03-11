package jting.zhao.java.util.concurrent;


import com.google.gson.Gson;
import jting.zhao.OpenTSDBClient;
import jting.zhao.core.TSData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaojunting1 on 2018/9/20
 */
public class IThreadPoolMonitor {

    private static Logger LOGGER = LoggerFactory.getLogger(IThreadPoolMonitor.class);

    private static IThreadPoolMonitor instance = new IThreadPoolMonitor();

    private ScheduledExecutorService monitor ;

    private static ConcurrentHashMap<String,IThreadPoolExecutor> pools ;

    private IThreadPoolMonitor() {
        pools = new ConcurrentHashMap<String, IThreadPoolExecutor>();

        monitor = Executors.newSingleThreadScheduledExecutor();
        monitor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
               doMonitor();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }

    public static void registerWithId(IThreadPoolExecutor threadPoolExecutor){
        pools.put(threadPoolExecutor.getId(), threadPoolExecutor);
    }

    public static void unRegisterWithId(IThreadPoolExecutor threadPoolExecutor){
        pools.remove(threadPoolExecutor.getId(), threadPoolExecutor);
    }

    public static void registerWithName(IThreadPoolExecutor threadPoolExecutor){
        pools.put(threadPoolExecutor.getName(), threadPoolExecutor);
    }

    public static void unRegisterWithName(IThreadPoolExecutor threadPoolExecutor){
        pools.remove(threadPoolExecutor.getName(), threadPoolExecutor);
    }

    private void doMonitor(){
        ConcurrentHashMap<String, IThreadPoolExecutor> executors = pools;
        Set<Map.Entry<String, IThreadPoolExecutor>> entries = executors.entrySet();

        List<IThreadPoolExecutor.PoolInfo> infos = new ArrayList<IThreadPoolExecutor.PoolInfo>();
        for(Map.Entry<String, IThreadPoolExecutor> each : entries){
            IThreadPoolExecutor poolExecutor = each.getValue();
            infos.add(poolExecutor.poolInfo());
        }
        LOGGER.info("IThreadPoolMonitor->" + new Gson().toJson(infos));

        new OpenTSDBClient().put(buildTSDatas(infos));
    }


    private List<TSData> buildTSDatas(List<IThreadPoolExecutor.PoolInfo> infos){
        List<TSData> datas = new ArrayList<TSData>();
        for(IThreadPoolExecutor.PoolInfo info : infos){
            datas.addAll(buildTSDatas(info));
        }
        return datas;
    }

    private List<TSData> buildTSDatas(IThreadPoolExecutor.PoolInfo info){
        List<TSData> datas = new ArrayList<TSData>();
        TSData data = new TSData<Integer>("poolSize",info.date.getTime(), info.poolSize);
        data.addTag("ip", "127.0.0.1");
        data.addTag("name", info.name);
        datas.add(data);

        TSData data1 = new TSData<Long>("completedTaskCount",info.date.getTime(), info.completedTaskCount);
        data1.addTag("ip", "127.0.0.1");
        data1.addTag("name", info.name);
        datas.add(data1);

        TSData data2 = new TSData<Integer>("queueSize",info.date.getTime(), info.queueSize);
        data2.addTag("ip", "127.0.0.1");
        data2.addTag("name", info.name);
        datas.add(data2);

        return datas;
    }
}
