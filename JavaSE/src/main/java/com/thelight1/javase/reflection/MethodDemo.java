package com.thelight1.javase.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class MethodDemo {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = Class.forName("com.thelight1.javase.reflection.Teacher");
        Method method = clazz.getDeclaredMethod("setTitle", String.class);

        //test invoke
        Teacher teacher = new Teacher("hanMeimei");
        System.out.println(teacher.getTitle());
        method.invoke(teacher, "高级教师");
        System.out.println(teacher.getTitle());

        //test setAccessible
        Method method1 = clazz.getDeclaredMethod("privateMethod");
        method1.setAccessible(true);
        method1.invoke(teacher);

        System.out.println(method.getDeclaringClass());

    }


}
