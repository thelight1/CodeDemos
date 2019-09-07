package com.thelight1.attribute;

import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Object> LOGIN = AttributeKey.newInstance("login");
}
