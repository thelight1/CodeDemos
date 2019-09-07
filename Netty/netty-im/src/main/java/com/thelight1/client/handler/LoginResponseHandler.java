package com.thelight1.client.handler;

import com.thelight1.protocol.request.LoginRequestPacket;
import com.thelight1.protocol.response.LoginResponsePacket;
import com.thelight1.session.Session;
import com.thelight1.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + "：客户端开始登录");
//
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("username111");
//        loginRequestPacket.setPassword("password111");
//
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + username +"]登录成功，userId为" + userId);
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            System.out.println("[" + username + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
