package com.thelight1;

import com.thelight1.spi.ObjectSerializer;
import com.thelight1.spi.ObjectSerializerException;


/**
 * 参考：Java SPI机制详解
 * https://juejin.im/post/5af952fdf265da0b9e652de3
 */
public class SerializerServiceTest {

    public static void main(String[] args) {
       serializerTest();
    }

    public static void serializerTest() throws ObjectSerializerException {
        SerializerService serializerService = new SerializerService();
        ObjectSerializer objectSerializer = serializerService.getObjectSerializer();
        System.out.println(objectSerializer.getSchemeName());
    }
}
