package com.thelight1;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private static class Worker extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "开始等待其他线程");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "开始执行");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("aaaaaa");
            }
        });

        for (int i = 0; i < threadCount; i++) {
            System.out.println("创建工作线程" + i);
            Worker worker = new Worker(cyclicBarrier);
            worker.start();
        }
    }
}
/**
 原文链接：https://blog.csdn.net/qq_38293564/article/details/80558157
 */
