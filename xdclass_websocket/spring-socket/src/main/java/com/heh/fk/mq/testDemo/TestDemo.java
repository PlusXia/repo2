package com.heh.fk.mq.testDemo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class TestDemo {
    public static void main(String[] args) throws ParseException, CloneNotSupportedException {

        Person p = new Person(23, "zhang");
        Person p1 = (Person) p.clone();
        p.setAge(11);
        System.out.println(p);
        p1.setAge(22);
        System.out.println(p1);
        String s = p.getName().equals(p1.getName()) ? "浅拷贝" : "深拷贝";
        System.out.println(s);
        Map<String, String> map = new HashMap<>();

    }
}
