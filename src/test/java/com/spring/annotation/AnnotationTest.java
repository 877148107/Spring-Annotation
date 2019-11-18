package com.spring.annotation;

import com.spring.annotation.configuration.MyConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationTest {

    @Test
    public void configuration(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        Object person = applicationContext.getBean("person");
        System.out.println(person);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }
}
