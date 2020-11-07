package com.thelight1;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        int N = 10;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(N);

        for (int i = 0; i < N; i++) {
            new Thread(new Worker(start, done)).start();
        }

        System.out.println("N 个线程开始执行任务......");
        start.countDown();
        done.await();
        System.out.println("N 个线程执行任务完成了.....");
    }
}

/**
 * N 个线程开始执行任务......
 * Thread-0 is working
 * Thread-5 is working
 * Thread-2 is working
 * Thread-1 is working
 * Thread-6 is working
 * Thread-7 is working
 * Thread-8 is working
 * Thread-9 is working
 * Thread-3 is working
 * Thread-4 is working
 * N 个线程执行任务完成了.....
 */

class Worker implements Runnable{

    private CountDownLatch start;
    private CountDownLatch done;

    public Worker(CountDownLatch start, CountDownLatch done) {
        this.start = start;
        this.done = done;
    }

    @Override
    public void run() {
        try {
            start.await();
            doWork();
            done.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        System.out.println(Thread.currentThread().getName() + " is working");
    }
}
