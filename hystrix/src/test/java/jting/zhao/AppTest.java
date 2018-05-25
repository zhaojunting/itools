package jting.zhao;

/**
 * Unit test for simple App.
 */
public class AppTest {

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
}
