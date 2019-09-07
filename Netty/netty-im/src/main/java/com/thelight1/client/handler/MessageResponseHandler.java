package com.thelight1.client.handler;

import com.thelight1.protocol.response.MessageResonsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResonsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResonsePacket messageResonsePacket) throws Exception {
        System.out.println(new Date() + "：从服务端接口的数据为【"+ messageResonsePacket.getMessage() +"】");
    }
}
