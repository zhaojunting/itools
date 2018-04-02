package jting.zhao;

/**
 * Created by  on 2018/2/4.
 * 可靠消息伪代码
 */
public class ReliableMsg {

    /**
     * 分布式事务-可靠事件-本地事件表
     * 本地事件表方式业务系统和事件系统耦合比较紧密，额外的事件数据库操作也会给数据库带来额外的压力，可能成为瓶颈。
     */
    //TODO: 挂@Transaction
    public void t1(){

        //TODO: aDao.save();

        //TODO:
        sendMsg();
        //TODO: bDao.update();
    }

    private void sendMsg(){
        //TODO: 检测当前是否在事务中

        //TODO: 注册事务hook

        //TODO: 插入事件日志-待发送

    }

    private void afterCommit(){
        //TODO: 调用producer.send() 发送消息

        //TODO: 更新事件日志-已发送
    }

    //TODO : 采取措施防止和 afterCommit之间的并发,分布式锁即可(双重校验)，基本无争用
    private void reSend(){
        //TODO: 读取事件日志-待发送

        //TODO: 调用producer.send()发送消息

        //TODO: 更新事件日志-已发送
    }

    /**
     * TODO: 具体消息中间件原理？推，拉？是长连接么？会等消费者请求结束么？
     * 事件幂等
     * 1. 事件本身幂等：例如把某字段更新到目标值，此时需要保证事件顺序，可使用全局递增Id，防止被覆盖!
     * 2. 事件本身不具备幂等性：需要根据业务ID记录事件处理日志
     *      并发类事件的幂等保证：事件丢弃
     *      非并发类事件的幂等保证：查询事件处理结果
     */
    private void idempotent(){
        //TODO:  注意消费者内部RPC其他业务服务的情况(比如付款接口)，RPC子接口非幂等的情况下，绝对不能重复调用!!!
        //1.先查询事件日志，已完成直接Over

        //2.插入事件日志(独立事务)--待处理(消费者+业务ID，挂唯一性约束，一个事件可能有不同的消费者)
        //  异常吃掉，此时消费者已经知道要处理此事件了，无需重复通知

        //3.业务处理RPC（TODO: 此步骤是重点）

        //TODO: 本地事务开启
        //4.1 本地业务
        //4.2 更新事件日志--已完成
        //TODO: 本地事务提交  此步骤可能出现异常!
        //TODO: 需要提供补偿机制保证消息处理完成，并且是否重复调用RPC子服务需要根据查询接口明确返回结果决定。
    }
}
