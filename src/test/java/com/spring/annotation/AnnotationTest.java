package com.spring.annotation;

import com.spring.annotation.aop.MyCalculate;
import com.spring.annotation.bean.Color;
import com.spring.annotation.bean.Person;
import com.spring.annotation.configuration.MyAopConfiguration;
import com.spring.annotation.configuration.MyConfiguration;
import com.spring.annotation.configuration.MyProfileConfiguration;
import com.spring.annotation.configuration.MyPropertyValuesConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *   
 *
 * @ClassName: AnnotationTest
 * =================================================
 * @Description: IOC测试
 *              注解的组件是否加入IOC容器中
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:31        
 * @Version: V1.0
 *     
 */
public class AnnotationTest {

    @Test
    public void configuration(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void propertyValues(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyPropertyValuesConfiguration.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
    }

    @Test
    public void profile(){
//        //使用无参构造器创建一个容器
//        AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext();
//        //设置需要激活的环境
//        applicationContext1.getEnvironment().setActiveProfiles("red","yellow");
//        //注册主配置类
//        applicationContext1.register(MyProfileConfiguration.class);
//        //刷新启动器
//        applicationContext1.refresh();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyProfileConfiguration.class);
        applicationContext.getEnvironment().setActiveProfiles("red","yellow");
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
//        Color color = (Color) applicationContext.getBean("color");
//        System.out.println(color);
    }

    @Test
    public void aop(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyAopConfiguration.class);
        MyCalculate myCalculate = applicationContext.getBean(MyCalculate.class);
        myCalculate.division(9,0);
    }
}
