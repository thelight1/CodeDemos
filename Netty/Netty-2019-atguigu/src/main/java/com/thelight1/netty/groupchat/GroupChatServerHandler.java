package com.thelight1.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //表示连接建立，一旦建立，第一个被执行
    //将当前channel加入channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date()) + "[客户端]" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    //断开连接，将xx客户离开的消息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date()) + "[客户端]" + channel.remoteAddress() + "离开了\n");
    }

    //表示channel处于活动的状态，提示 xx上线了
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()) + ctx.channel().remoteAddress() + "上线了");
    }

    //表示channel处于非活动状态, 提示 xx离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()) + ctx.channel().remoteAddress() + "离线了");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        final Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (ch != channel) {
               ch.writeAndFlush(simpleDateFormat.format(new Date()) + "[客户]" + channel.remoteAddress() + "发送了消息：" + msg + "\n");
            } else {
               ch.writeAndFlush(simpleDateFormat.format(new Date()) + "[自己]发送了消息：" + msg + "\n");
            }
        });
    }
}
