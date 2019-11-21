package com.spring.annotation;

import com.spring.annotation.configuration.MyConfiguration;
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
}
