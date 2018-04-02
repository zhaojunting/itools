package jting.zhao.questions.test1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojt on 2018/2/12.
 */
public class A implements Observable {

    private List<Observer> observers = new ArrayList<Observer>();

    @Override
    public synchronized void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public synchronized void delObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers){
            observer.observe();
        }
    }

    public void someHappend(){
        //DO Something

        notifyObservers();
    }

    public static void main(String[] args) {
        java.util.Observer observer = null;
        java.util.Observable observable = null;
    }
}
