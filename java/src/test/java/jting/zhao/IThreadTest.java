package jting.zhao;

import jting.zhao.java.lang.IThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaojt on 2018/1/25.
 */
public class IThreadTest extends ITest {


    private static Logger LOGGER = LoggerFactory.getLogger(IThreadTest.class);

    public static void main(String[] args) {
        t2();

        threadSleep(1000);
    }

    private static void t1(){
        IThread t1 = new IThread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running...");
            }
        });

        t1.start();

        t1.interrupt();//TODO: IThread super.interrupt(); 后面LOGGER 会栈溢出？什么鬼
    }

    //测试 t2中断t1
    private static void t2(){
        IThread t1 = null;
        IThread t2 = null;


        t1 = new IThread(new NumberedTask(1) {
            @Override
            public void run() {
                LOGGER.info(getId() +" running...");
//                threadSleep(2000);//java.java.lang.InterruptedException: sleep interrupted

                int i = 0;
                while (i++ >= 0){
                    LOGGER.info(getId() +" running...");
                    if(i > 20){
                        try {
                            this.wait();  //Exception in thread "Thread-1" java.java.lang.IllegalMonitorStateException
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        final IThread finalT = t1;
        t2 = new IThread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info(" t2 interrupt .");
                finalT.interrupt();
            }
        });

        t1.start();
        threadSleep(1);
        t2.start();


    }
}
