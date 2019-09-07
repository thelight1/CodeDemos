package com.thelight1.client;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.PacketCodeC;
import com.thelight1.protocol.request.LoginRequestPacket;
import com.thelight1.protocol.request.MessageRequestPacket;
import com.thelight1.protocol.response.LoginResponsePacket;
import com.thelight1.protocol.response.MessageResonsePacket;
import com.thelight1.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登录");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("username111");
        loginRequestPacket.setPassword("password111");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket)packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + "：登录成功");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + "：登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
        if (packet instanceof MessageResonsePacket) {
            MessageResonsePacket messageResonsePacket = (MessageResonsePacket) packet;
            System.out.println(new Date() + "：从服务端接口的数据为【"+ messageResonsePacket.getMessage() +"】");
        }
    }
}
