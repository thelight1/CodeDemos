package com.thelight1.util;

import com.thelight1.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Object> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
