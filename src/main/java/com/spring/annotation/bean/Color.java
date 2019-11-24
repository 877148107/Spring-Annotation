package com.spring.annotation.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @ClassName: Color
 * =================================================
 * @Description: 实现ApplicationContextAware:获取传入的IOC容器
 *               实现BeanNameAware：获取对应的beanname值
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/20 23:14        
 * @Version: V1.0
 *     
 */
public class Color implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private String colorName;

    public Color(String colorName) {
        this.colorName = colorName;
    }

    private ApplicationContext applicationContext;

    public void color(){
        System.out.println("color 构造方法");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的IOC容器。。。。。。。。。。。");
        this.applicationContext = applicationContext;
    }

    public void setBeanName(String name) {
        System.out.println("bean，name:"+name);
    }

    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String s = stringValueResolver.resolveStringValue("解析的字符串：${os.name},#{100*100}");
        System.out.println(s);
    }

    @Override
    public String toString() {
        return "Color{" +
                "colorName='" + colorName + '\'' +
                ", applicationContext=" + applicationContext +
                '}';
    }
}
