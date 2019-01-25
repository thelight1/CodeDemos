package com.thelight1.instrument;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class PeopleClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("load class:" + className);
        if ("com/thelight1/instrument/People".equals(className)) {
            try {
                CtClass ctClass = ClassPool.getDefault().get(className.replace('/', '.'));
                CtMethod sayHelloMethod = ctClass.getDeclaredMethod("sayHello");
                sayHelloMethod.insertBefore("System.out.println(\"before sayHello\");");
                sayHelloMethod.insertAfter("System.out.println(\"after sayHello\");");

                return ctClass.toBytecode();

            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}