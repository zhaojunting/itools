package jting.zhao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName: BlockingQueueTest
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/11 12:50
 * Inc.All rights reserved.
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(10);

        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
        linkedBlockingQueue.put("123");
        String take = linkedBlockingQueue.take();
    }


}
