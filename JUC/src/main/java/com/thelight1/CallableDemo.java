package com.thelight1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable可以返回返回值
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());

        new Thread(futureTask, "AA").start();
        /**
         * 多个Thread包裹一个futureTask，futureTask的call方法只会运行一次
         */
//        new Thread(futureTask, "BB").start();

        while (!futureTask.isDone()) {
        }

        Integer ret = futureTask.get();
        System.out.println(ret);
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in");
        return 1024;
    }
}
