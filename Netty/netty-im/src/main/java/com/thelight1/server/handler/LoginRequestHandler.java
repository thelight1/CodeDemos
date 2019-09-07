package com.thelight1.server.handler;

import com.thelight1.protocol.PacketCodeC;
import com.thelight1.protocol.request.LoginRequestPacket;
import com.thelight1.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
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
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean validate(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
