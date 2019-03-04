package com.thelight1.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.Future;


/**
 * Created by zhanghan18 on 02/03/2019.
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("ThreadName:" + Thread.currentThread().getName());
        return "hello " + name + "!";
    }

    public static void main(String[] args) throws Exception {
        String s = new CommandHelloWorld("Bob").execute();
        System.out.println(s);
//
//        Future<String> s2 = new CommandHelloWorld("Bob").queue();
//        System.out.println(s2.get());
//
//        Observable<String> s3 = new CommandHelloWorld("Bob").observe();
//        System.out.println(s3);
    }
}
