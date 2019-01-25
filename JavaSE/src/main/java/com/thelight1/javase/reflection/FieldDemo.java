package com.thelight1.javase.reflection;

import java.lang.reflect.Field;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class FieldDemo {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName("com.thelight1.javase.reflection.Teacher");
        Field field = clazz.getDeclaredField("title");
    }
}
