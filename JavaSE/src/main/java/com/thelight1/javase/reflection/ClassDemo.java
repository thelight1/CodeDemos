package com.thelight1.javase.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class ClassDemo {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        Class clazz = Class.forName("com.thelight1.javase.reflection.Teacher");

//        printMethodsInfo(clazz);
//        printFieldsInfo(clazz);
        printConstructorsInfo(clazz);

        System.out.println();
    }

    /**
     * Constructor
     */
    private static void printConstructorsInfo(Class clazz) {
        System.out.println("====getConstructors=====");
        //public的构造方法
        for (Constructor constructor : clazz.getConstructors()) {
            System.out.println(constructor);
        }
        //所有的构造方法
        System.out.println("=====getDeclaredConstructors=====");
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            System.out.println(constructor);
        }
    }

    /**
     * Field
     */
    private static void printFieldsInfo(Class clazz) throws NoSuchFieldException {
        System.out.println("=====getFields=====");
        for (Field field : clazz.getFields()) {
            System.out.println(field);
        }

        System.out.println("=====getDeclaredFields=====");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field);
        }

        System.out.println("=====getField(String name)=====");
        try {
            Field field = clazz.getField("name");
            System.out.println(field);
        } catch (NoSuchFieldException e) {
            System.out.println(e);
        }

        System.out.println("=====getDeclaredField(String name)=====");
        try {
            Field field2 = clazz.getDeclaredField("name");
            System.out.println(field2);
        } catch (NoSuchFieldException e) {
            System.out.println(e);
        }
    }

    /**
     * Method
     */
    private static void printMethodsInfo(Class clazz) throws NoSuchMethodException {
        //获取方法
        System.out.println("======getMethods=====");
        for (Method method : clazz.getMethods()) {
            System.out.println(method);
        }

        System.out.println("======getDeclaredMethods=====");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method);
        }

        System.out.println("======getMethod=====");
        System.out.println(clazz.getMethod("getName"));
        Class partype = String.class;
        System.out.println(clazz.getMethod("setName", partype));
        Class partype2 = int.class;
        System.out.println(clazz.getMethod("setAge", partype2));

        System.out.println("=====getDeclaredMethod=====");
        System.out.println(clazz.getDeclaredMethod("getName"));
        System.out.println(clazz.getDeclaredMethod("setName", partype));
    }
}




