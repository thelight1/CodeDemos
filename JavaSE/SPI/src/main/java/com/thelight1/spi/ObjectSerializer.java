package com.thelight1.spi;

public interface ObjectSerializer {

    byte[] serialize(Object obj) throws ObjectSerializerException;

    <T> T deSerialize(byte[] param, Class<T> clazz) throws ObjectSerializerException;

    String getSchemeName();
}

