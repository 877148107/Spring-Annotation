package com.spring.annotation.configuration;

import com.spring.annotation.bean.Color;
import com.spring.annotation.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: MyPropertyValuesConfiguration
 * =================================================
 * @Description: 属性赋值
 *
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/23 23:25
 * @Version: V1.0
 */
@PropertySource(value = {"classpath:/springboot.properties"})
@Configuration
public class MyPropertyValuesConfiguration {


    @Bean
    public Color color(){
        return new Color("");
    }

    @Primary
    @Bean
    public Person person(){
        return new Person();
    }
}
