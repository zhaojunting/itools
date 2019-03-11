package jting.zhao;

import com.google.gson.Gson;
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


    protected static abstract class NumberedTask implements Runnable{

        private int id;

        public NumberedTask(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }


    }

    public void log(Object object){
        System.out.println(toJson(object));
    }

    public String toJson(Object object){
        return new Gson().toJson(object);
    }

}
