package com.thelight1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 程序输出结果：
 * AAA put 1
 * BBB 获取到1
 * AAA put 2
 * BBB 获取到2
 * AAA put 3
 * BBB 获取到3
 *
 * 可以看到是BBB线程消费完，AAA线程再放入的。
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingDeque = new SynchronousQueue<>();

        /**
         * AAA线程直接放入3个元素，中间没有sleep
         */
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " put 1");
                blockingDeque.put("1");

                System.out.println(Thread.currentThread().getName() + " put 2");
                blockingDeque.put("2");

                System.out.println(Thread.currentThread().getName() + " put 3");
                blockingDeque.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        /**
         * BBB线程消费前要先sleep 5秒
         */
        new Thread(() -> {
            try {
                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + " 获取到" + blockingDeque.take());

                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + " 获取到" + blockingDeque.take());

                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + " 获取到" + blockingDeque.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
