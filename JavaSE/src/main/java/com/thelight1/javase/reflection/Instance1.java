package com.thelight1.javase.reflection;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class Instance1 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class cls = Class.forName("com.thelight1.javase.reflection.A");
        boolean b1 =  cls.isInstance(new Integer(37));

        System.out.println(b1);

        boolean b2 = cls.isInstance(new A());
        System.out.println(b2);
    }
}

class A {}