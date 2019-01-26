package com.thelight1.javase.socket.nio;

import java.io.IOException;

/**
 * NIO: Non-block IO 非阻塞IO
 *
 * NIO类库
 * 1）Buffer缓冲区
 * 访问NIO的数据，都是通过缓冲区操作的
 * 最常用ByteBuffer
 *
 * 2）Channel通道
 * Channel是全双工的，支持读、写、同时读写
 * （流InputStream、OutputStream是单向的）
 *
 * 3）Selector多路复用器(最重要的概念)
 * Selector会不断轮训注册在其上的Channel，
 * 如果某个Channel有TCP连接接入、读、写事件，就会被轮询出来，
 * 然后通过SelectionKey可以获取就绪的channel集合
 *
 */
public class TimeServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}

