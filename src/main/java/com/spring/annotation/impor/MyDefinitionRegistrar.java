package com.spring.annotation.impor;

import com.spring.annotation.bean.Dog;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 *   
 *
 * @ClassName: MyDefinitionRegistrar
 * =================================================
 * @Description: 自定义手动注册bean到容器中
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:38        
 * @Version: V1.0
 *     
 */
public class MyDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        boolean dog = registry.containsBeanDefinition("dog");
        if (!dog) {
            //指定bean
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Dog.class);
            registry.registerBeanDefinition("dog",beanDefinition);
        }
    }
}
