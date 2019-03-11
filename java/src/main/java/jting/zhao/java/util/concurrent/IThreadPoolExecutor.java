package jting.zhao.java.util.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zhaojt on 2018/1/20.
 */
public class IThreadPoolExecutor extends ThreadPoolExecutor{

    private Logger LOGGER = LoggerFactory.getLogger(IThreadPoolExecutor.class);

    private String id;

    private String ip;

    private String name;

    public IThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);

    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    @Override
    public void shutdown() {
        LOGGER.info(journal());
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        LOGGER.info(journal());
        return super.shutdownNow();
    }

    @Override
    protected void terminated() {
        super.terminated();
        LOGGER.info(journal());
        LOGGER.info(getName() + " is terminated ");
    }

    public String getName() {
        return name;
    }

    public IThreadPoolExecutor name(String name) {
        this.name = name;
        return this;
    }

    class PoolInfo{
        String id;
        String ip;
        String name;
        int poolSize;       //当前线程数
        int queueSize;      //队列大小
        int activeCount;    //主动执行任务的线程数（近似）
        long taskCount;     //计划执行任务总数（近似）
        int corePoolSize;   //设置的核心线程数
        int largestPoolSize;//曾经最大线程数（近似）
        int maximumPoolSize;//允许最大线程数
        long completedTaskCount;//已经完成任务总数（近似）
        Date date;
        int timestamp;

        public PoolInfo() {
            this.id = getId();
            this.ip = getIp();
            this.name = getName();
            this.poolSize = getPoolSize();
            this.queueSize = getQueue().size();
            this.activeCount = getActiveCount();
            this.taskCount = getTaskCount();
            this.corePoolSize = getCorePoolSize();
            this.largestPoolSize = getLargestPoolSize();
            this.maximumPoolSize = getMaximumPoolSize();
            this.completedTaskCount = getCompletedTaskCount();
            this.date = new Date();
        }
    }
    public String journal(){
        StringBuilder journal = new StringBuilder();
        journal.append(getName());
        journal.append(" :PoolSize=").append(getPoolSize());
        journal.append(",QueueSize=").append(getQueue().size());
        journal.append(",ActiveCount=").append(getActiveCount());
        journal.append(",TaskCount=").append(getTaskCount());
        journal.append(",LargestPoolSize=").append(getLargestPoolSize());
        journal.append(",CompletedTaskCount=").append(getCompletedTaskCount());
        journal.append(".");

        return journal.toString();
    }

    public IThreadPoolExecutor setMonitorable(){
//        if(monitor != null){
//            return this;
//        }
//        monitor = Executors.newSingleThreadScheduledExecutor();
//
//        monitor.scheduleWithFixedDelay(new Runnable() {
//
//            @Override
//            public void run() {
////                LOGGER.info("Monitor[" + journal() + "]");
//                OpenTSDBClient client = new OpenTSDBClient();
//            }
//        }, 500, 1500, TimeUnit.MILLISECONDS);

        IThreadPoolMonitor.registerWithName(this);

        return this;
    }

    //核心线程闲置回收
    public IThreadPoolExecutor allowCoreThreadTimeOut(){
        super.allowCoreThreadTimeOut(true);
        return this;
    }

    @Override
    protected void finalize() {
        LOGGER.info(getName() + " finalize before !");
        super.finalize();
        LOGGER.info(getName() + " finalize over !");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PoolInfo poolInfo(){
        return new PoolInfo();
    }
}
