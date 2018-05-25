package jting.zhao.hystrix.commands;

import com.netflix.hystrix.HystrixObservableCommand;
import jting.zhao.service.impl.IRpcServiceA;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.netflix.hystrix.HystrixCommandGroupKey.*;

/**
 * Created by zhaojunting1 on 2018/5/22
 */
public class CommandHelloWorldObserver extends HystrixObservableCommand<String> {

    private final String name;

    public CommandHelloWorldObserver(String name) {
        super(Factory.asKey("ExampleGroup_2"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here
//                        observer.onNext("Hello");
//                        observer.onNext(name + "!");
                        new IRpcServiceA().do2();
                        observer.onNext("success");
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribeOn(Schedulers.io());
    }
}
