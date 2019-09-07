package com.thelight1.client.handler;

import com.thelight1.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResonsePacket) throws Exception {
        String fromUserId = messageResonsePacket.getFromUserId();
        String fromUsername = messageResonsePacket.getFromUsername();
        System.out.println("接收到来自[" + fromUserId + ":" + fromUsername + "]的消息:" + messageResonsePacket.getMessage());
    }
}
