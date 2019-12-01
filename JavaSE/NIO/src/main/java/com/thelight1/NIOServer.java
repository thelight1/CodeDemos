package com.thelight1;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NIOServer {

    private void sleepAWhile() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_WRITE);

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel)key.channel();

                    String data = new Date().toString();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(data.getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();

                    System.out.println("发送给["+ socketChannel.getRemoteAddress() +"]的数据：" + data);

                    sleepAWhile();
                    channel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println("接收到["+ socketChannel.getRemoteAddress() +"]的数据：" + new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }

                    sleepAWhile();
                    channel.register(selector, SelectionKey.OP_WRITE);
                }

                iterator.remove();
            }

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(new Date().toString().getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.close();
    }

    @Test
    public void server() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9898));

        System.out.println("服务器启动，在9898端口开始监听..........");

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
                    System.out.println("接收到来自[" + socketChannel.getRemoteAddress() + "]的连接");

                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println("接收到["+ socketChannel.getRemoteAddress() +"]的数据：" + new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }

                    String responseStr = "ping";
                    byte[] resp = responseStr.getBytes("utf-8");
                    ByteBuffer responseBuffer = ByteBuffer.allocate(resp.length);
                    responseBuffer.put(resp);
                    responseBuffer.flip();

                    socketChannel.register(selector, SelectionKey.OP_WRITE, responseBuffer);
                }
                if (key.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    socketChannel.write(byteBuffer);

                    System.out.println("发送给["+ socketChannel.getRemoteAddress() +"]的数据：" + new String(byteBuffer.array(), 0, byteBuffer.array().length));
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                iterator.remove();
            }
        }
    }

}
