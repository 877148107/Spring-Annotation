package com.spring.annotation.configuration;

import com.spring.annotation.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类=原来的xml配置文件
 */
@Configuration /**告诉Spring这是一个配置类**/
public class MyConfiguration {

    @Bean
    public Person person(){
        return new Person("张三",19);
    }
}
