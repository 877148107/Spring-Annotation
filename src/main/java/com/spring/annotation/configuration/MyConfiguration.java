package com.spring.annotation.configuration;

import com.spring.annotation.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ������=ԭ����xml�����ļ�
 */
@Configuration /**����Spring����һ��������**/
public class MyConfiguration {

    @Bean
    public Person person(){
        return new Person("����",19);
    }
}
