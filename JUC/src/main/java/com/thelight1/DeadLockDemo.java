package com.thelight1;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhanghan18 on 06/01/2019.
 */
public class DeadLockDemo extends Thread {

    static ReentrantLock lockA = new ReentrantLock();
    static ReentrantLock lockB = new ReentrantLock();

    private Object myLock;

    public DeadLockDemo(Object myLock) {
        this.myLock = myLock;
        if (myLock == lockA) {
            this.setName("Thread-hold-lockA");
        }
        if (myLock == lockB) {
            this.setName("Thread-hold-lockB");
        }
    }

    @Override
    public void run() {
        if (myLock == lockA) {
            try {
                lockA.lockInterruptibly();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                lockB.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "成功获取到lockB");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "获取lockB失败");
            } finally {
                if (lockA.isHeldByCurrentThread()) {
                    lockA.unlock();
                }
                if (lockB.isHeldByCurrentThread()) {
                    lockB.unlock();
                }
            }
        }

        if (myLock == lockB) {
            try {
                lockB.lockInterruptibly();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                lockA.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "成功获取到lockA");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "获取lockA失败");
            } finally {
                if (lockA.isHeldByCurrentThread()) {
                    lockA.unlock();
                }
                if (lockB.isHeldByCurrentThread()) {
                    lockB.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        DeadLockDemo hasLockA = new DeadLockDemo(lockA);
        DeadLockDemo hasLockB = new DeadLockDemo(lockB);
        hasLockA.start();
        hasLockB.start();
        Thread.sleep(100000);
    }
}

