package com.thelight1.server;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.PacketCodeC;
import com.thelight1.protocol.request.LoginRequestPacket;
import com.thelight1.protocol.request.MessageRequestPacket;
import com.thelight1.protocol.response.LoginResponsePacket;
import com.thelight1.protocol.response.MessageResonsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());

            if (validate(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("用户名密码校验失败");
                System.out.println(new Date() + ": 登录失败");
            }
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
        if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket)packet;

            MessageResonsePacket messageResonsePacket = new MessageResonsePacket();
            messageResonsePacket.setVersion(messageRequestPacket.getVersion());
            messageResonsePacket.setMessage(new Date() + "：服务端返回数据["+ messageRequestPacket.getMessage()+"]");

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResonsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean validate(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
