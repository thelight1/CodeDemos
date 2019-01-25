package com.thelight1.javase.reflection;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class Person {
    protected String name;
    protected int age;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
