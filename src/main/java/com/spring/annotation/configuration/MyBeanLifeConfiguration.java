package com.spring.annotation.configuration;

import com.spring.annotation.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MyBeanLifeConfiguration
 * =================================================
 * @Description: bean的生命周期
 * Bean的生命周期：
 *              bean创建----初始化---销毁的过程
 * 1)、指定初始化、销毁的方法
 * 1.构造:对象创建
 *      单实例：在容器启动的时候就创建对象
 *      多实例：容器启动的时候不创建，每次获取的时候创建
 * 2.初始化：对象创建完成并赋值后调用初始化方法
 * 3.销毁：
 *      单实例：容器关闭后就销毁
 *      多实例：容器关闭后不会销毁，容器不管理这个多实例bean
 * 2)、通过实现初始化InitializingBean和销毁DisposableBean的接口
 * 3)、可以使用JSR250；@PostConstruct：在bean创建完成并且属性赋值完成；来执行初始化方法,@PreDestroy：在容器销毁bean之前通知我们进行清理工作
 * 4)、BeanPostProcessor:bean的后置处理器
 *     初始化之前调用postProcessBeforeInitialization
 *     初始化之后调用postProcessAfterInitialization
 * 4)、BeanPostProcessor原理：
 *     1.创建容器AnnotationConfigApplicationContext
 *     2.初始化自定义后置处理器创建并初始化MyBeanPostProcessor
 *     3.doCreateBean(beanName, mbdToUse, args)创建bean
 *     4.populateBean(beanName, mbd, instanceWrapper)对bean进行赋值等操作
 *     5.initializeBean(beanName, exposedObject, mbd)初始化bean
 *     6.applyBeanPostProcessorsBeforeInitialization(bean, beanName)调用初始化bean之前的方法
 *     7.invokeInitMethods(beanName, wrappedBean, mbd)初始化bean
 *     8.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName)调用初始化bean之后的方法
 *
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/23 12:41
 * @Version: V1.0
 */
@ComponentScan("com.spring.annotation.bean")
@Configuration
public class MyBeanLifeConfiguration {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
