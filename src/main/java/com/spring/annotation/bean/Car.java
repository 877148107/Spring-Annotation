package com.spring.annotation.bean;

/**
 * @ClassName: car
 * =================================================
 * @Description: TODO
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/23 12:51
 * @Version: V1.0
 */
public class Car {

    public void car(){
        System.out.println("car 创建");
    }

    public void init(){
        System.out.println("car 初始化");
    }

    public void destroy(){
        System.out.println("car 销毁");
    }
}
