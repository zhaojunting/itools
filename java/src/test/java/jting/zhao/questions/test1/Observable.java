package jting.zhao.questions.test1;

import jting.zhao.questions.test1.Observer;

/**
 * Created by 19669 on 2018/2/12.
 */
public interface Observable {

    abstract void addObserver(Observer observer);

    abstract void delObserver(Observer observer);

    abstract void notifyObservers();
}
