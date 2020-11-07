package com.thelight1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的使用:适用于读多写少的场景
 * 场景之一：缓存
 *
 * 多个线程读取共享资源没有问题；
 * 如果有一个线程想去写共享资源，其他线程就不能进行读或写
 * 总结：
 *    读读能共存
 *    读写不能共存
 *    写写不能共存
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI +"");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, String.valueOf(i + 5)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入:" + key);
            map.put(key, value);

            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + " 写入完成");
        } catch (Exception e) {
        } finally {
           rwLock.writeLock().unlock();
        }
   }

    public Object get(String key) {
        Object result = null;
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取:" + key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完毕:" + result);
        } catch (Exception e) {

        } finally {
           rwLock.readLock().unlock();
        }
        return result;
    }
}
