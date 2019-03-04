package com.thelight1.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by zhanghan18 on 02/03/2019.
 */
public class CommandHelloFailure extends HystrixCommand<String> {

    private String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("this method always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello failure " + name + "!";
    }

    public static void main(String[] args) {
        String s = new CommandHelloFailure("Bob").execute();
        System.out.println(s);
    }
}

