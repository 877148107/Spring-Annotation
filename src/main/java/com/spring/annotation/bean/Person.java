package com.spring.annotation.bean;

import org.springframework.beans.factory.annotation.Value;


public class Person {

    /**
     * 基本数值
     */
    @Value("李四")
    private String name;

    /**
     * 可以写SpEL； #{}
     */
    @Value("#{18-1}")
    private int age;

    /**
     * 可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
     */
    @Value("${person.birthday}")
    private String birthday;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
