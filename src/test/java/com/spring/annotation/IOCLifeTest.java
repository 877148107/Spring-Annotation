package com.spring.annotation;

import com.spring.annotation.configuration.MyBeanLifeConfiguration;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: IOCLifeTest
 * =================================================
 * @Description: IOC测试类
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/23 12:57
 * @Version: V1.0
 */
public class IOCLifeTest {

    @Test
    public void iocTest(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyBeanLifeConfiguration.class);
        System.out.println("IOC容器创建完成");

        applicationContext.close();
    }
}
