package jting.zhao.java.util.concurrent;

import jting.zhao.java.lang.IThread;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhaojt on 2018/1/21.
 */
public class IThreadFactory implements ThreadFactory{


    @Override
    public Thread newThread(Runnable r) {
        return new IThread(r);
    }

}
