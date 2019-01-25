package com.thelight1.javase.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Constructor1 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName("com.thelight1.javase.reflection.Teacher");

        Constructor constructor = clazz.getDeclaredConstructor();
        Teacher teacher = (Teacher)constructor.newInstance();
        teacher.setName("Lilei");

        System.out.println(teacher.getName());
    }
}
