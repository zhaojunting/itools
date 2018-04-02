package jting.zhao.java.lang;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhaojt on 2018/1/21.
 */
public class IThread extends Thread {

    private static Logger LOGGER = LoggerFactory.getLogger(IThread.class);

    public IThread(Runnable target) {
        super(target);
    }

    @Override
    public void interrupt() {
        LOGGER.info("IThread " + getName() + " interrupting! ");
        super.interrupt();
        LOGGER.info("IThread " + getName() + " interrupt over! ");
    }
}
