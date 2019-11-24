package com.spring.annotation.configuration;

import com.spring.annotation.aop.LogAspects;
import com.spring.annotation.aop.MyCalculate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName: MyAopConfiguration
 * =================================================
 * @Description: AOP的一个配置类
 * AOP面向切面编程【动态代理】
 *              指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 *  AOP的通知方法：前置通知startLog（注解方式：@Before）
 *               后置通知endLog（注解方式：@After）
 *               返回通知returnLog（注解方式：@AfterReturning）
 *               异常通知exceptionLog（注解方式：@AfterThrowing）
 *               环绕通知：动态代理，手动推进目标方法运行（joinPoint.procced()）（注解方式：@Around）
 * @Aspect:告诉Spring这是一个切面类，而不是普通的类
 * @EnableAspectJAutoProxy:开启基于注解的APO模式,在Spring中还有许多@Enablexxxx开启某一项功能
 *
 *
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/22 23:22
 * @Version: V1.0
 */
@EnableAspectJAutoProxy
@Configuration
public class MyAopConfiguration {

    @Bean
    public MyCalculate myCalculate(){
        return new MyCalculate();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
