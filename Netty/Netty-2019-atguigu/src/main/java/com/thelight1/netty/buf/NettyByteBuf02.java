package com.thelight1.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyByteBuf02 {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

        if (buf.hasArray()) {
            byte[] content = buf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println("byteBuf=" + buf);
        }
    }
}
