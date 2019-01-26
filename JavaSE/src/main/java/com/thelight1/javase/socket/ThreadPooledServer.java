package com.thelight1.javase.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一个基于线程池的SocketServer
 * 不足之处
 * 1）只能开，不能关
 * 2）线程池没有shutdown，容易线程池泄露
 *
 * 没有设置超时
 *
 * 其他的参考资料
 * http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html
 */
public class ThreadPooledServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPooledServer.class);

    public static void main(String[] args) throws Exception {
        ThreadPooledServer threadPooledServer = new ThreadPooledServer(12345);
        threadPooledServer.start();

        Thread.sleep(100000);
    }

    private AtomicBoolean running = new AtomicBoolean(false);
    private int port;

    //使用固定大小的线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("ThreadPooledServer-process-Thread");
            thread.setDaemon(true);
            return thread;
        }
    });

    public ThreadPooledServer(int port) {
        this.port = port;
    }

    public void start() {
        if (running.compareAndSet(false, true)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ServerSocket serverSocket = null;
                    try {
                        serverSocket = new ServerSocket(port);
                        LOGGER.info("ThreadPooledServer bind port:" + port);
                        while (true) {
                            Socket socket = serverSocket.accept();
                            processSocket(socket);
                        }
                    } catch (IOException e) {
                        LOGGER.error("ThreadPooledServer error accepting client connection!", e);
                    }
                }
            });
            thread.setName("ThreadPooledServer-listen-Thread");
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void processSocket(final Socket socket) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                     ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
                ) {
                    Object obj = inputStream.readObject();
                    String json = (String)obj;
                    LOGGER.info("ThreadPooledServer processSocket, received json is [{}]", json);
                    /**
                     * 这边是处理逻辑
                     */
                    String result = "this is the result";
                    LOGGER.info("ThreadPooledServer processSocket, result is [{}]", result);
                    outputStream.writeObject(result);
                } catch (IOException e) {
                    LOGGER.error("ThreadPooledServer processSocket error", e);
                } catch (ClassNotFoundException e) {
                    LOGGER.error("ThreadPooledServer processSocket error", e);
                } catch (Exception e) {
                    LOGGER.error("ThreadPooledServer processSocket error", e);
                } finally {
                    try {
                        socket.close();
                    } catch (Exception ex) {
                    }
                }
            }
        });
    }
}
