package jting.zhao;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/**
 * Created by zhaojt on 2018/1/21.
 */
public class ITest {

    public static void threadSleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static abstract class NumberedTask implements Runnable{

        private int id;

        public NumberedTask(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }


    }


}
