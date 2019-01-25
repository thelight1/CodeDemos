package com.thelight1.javassist;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * http://www.javassist.org/tutorial/tutorial.html
 */
public class Demo {

    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("test.Rectangle");
        cc.setSuperclass(pool.get("test.Point"));
        cc.writeFile();
    }
}
