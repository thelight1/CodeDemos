package com.thelight1.javase.reflection;

/**
 * Created by zhanghan18 on 19/01/2019.
 */
public class Teacher extends Person {
    private String title;
    private String course;

    public Teacher() {

    }

    public Teacher(String name) {
        super(name);
    }

    Teacher(String name, String title, String course) {
        this(title, course);
        this.name = name;
    }
    private Teacher(String title, String cource) {
        this.title = title;
        this.course = cource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    private void privateMethod() {
        System.out.println("this is a private method");
    }
}
