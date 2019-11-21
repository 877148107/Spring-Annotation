package com.spring.annotation.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *   
 *
 * @ClassName: Conditional
 * =================================================
 * @Description: 自定义注解条件用来判断是否需要将组件添加到容器中
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:14        
 * @Version: V1.0
 *     
 */
public class MyConditional implements Condition {

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");
        if(property.contains("Windows")){
            return true;
        }
        return false;
    }
}
