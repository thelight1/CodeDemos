package com.thelight1.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhanghan18 on 26/01/2019.
 */

/**
 * 本hello world程序来自以下网址，感谢！
 * http://tutorials.jenkov.com/netty/index.html
 *
 * 但是运行上似乎有点问题。....
 */
public class NettyTCPServer {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("127.0.0.1", 9999));

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                /**
                 * 每次accept一个socketChannel的时候，将调用initChannel方法
                 */
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HelloServerHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
