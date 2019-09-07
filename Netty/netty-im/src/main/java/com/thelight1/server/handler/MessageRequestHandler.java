package com.thelight1.server.handler;

import com.thelight1.protocol.request.MessageRequestPacket;
import com.thelight1.protocol.response.MessageResonsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResonsePacket messageResonsePacket = new MessageResonsePacket();
        messageResonsePacket.setVersion(messageRequestPacket.getVersion());
        messageResonsePacket.setMessage(new Date() + "：服务端返回数据["+ messageRequestPacket.getMessage()+"]");

        ctx.channel().writeAndFlush(messageResonsePacket);
    }
}
