package jting.zhao;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: IReentrantReadWriteLockTest
 * @Description: (这里用一句话描述这个类的作用)
 * @Author: zhaojt
 * @Date: 2018/3/19 23:45
 * Inc.All rights reserved.
 */
public class IReentrantReadWriteLockTest {

    static int i = 0;

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        lock.readLock().lock();
        lock.readLock().unlock(); //不可以直接进行锁升级

        lock.writeLock().lock();
        lock.readLock().lock();   //可以直接锁降级
        i++;
        lock.writeLock().unlock();
        lock.readLock().unlock();
    }
}
