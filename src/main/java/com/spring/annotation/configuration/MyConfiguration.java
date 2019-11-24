package com.spring.annotation.configuration;

import com.spring.annotation.bean.Color;
import com.spring.annotation.bean.MonkeyFactoryBean;
import com.spring.annotation.bean.Person;
import com.spring.annotation.conditional.MyConditional;
import com.spring.annotation.impor.MyDefinitionRegistrar;
import com.spring.annotation.impor.MyImportSelector;
import com.spring.annotation.service.AnnotationService;
import org.springframework.context.annotation.*;


/**
 *
 * @ClassName: Color
 * =================================================
 * @Description: Spring注解驱动
 *  *@Configuration:告诉Spring这是一个配置类,相当于原来的xml配置文件
 *  *@ComponentScan:指定要扫描的包，会把这个包下面注解的组件添加到容器中
 *  *@Import:快速给容器中导入一个组件,容器中就会自动注册这个组件，id默认是全类名
 *  *@MyImportSelector:自定义逻辑返回需要导入的组件
 *  *@MyDefinitionRegistrar:自定义手动注册bean到容器中
 *  *还可以是FactoryBean注册bean，1.默认获取到的是工厂bean调用getObject创建的对象，2.要获取工厂Bean本身，我们需要给id前面加一个&
 * 	 * 			&colorFactoryBean
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/20 23:14
 * @Version: V1.0
 */
@Configuration
@ComponentScan(value = {"com.spring.annotation.dao"})
@Import({AnnotationService.class, MyImportSelector.class, MyDefinitionRegistrar.class})
public class MyConfiguration {

    /**
     * *@Scope:调整作用域
     *    *@Scope("prototype")prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
     *                                   每次获取的时候才会调用方法创建对象；
     *    *@Scope("singleton")singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     *         			以后每次获取就是直接从容器（map.get()）中拿，
     *        request：同一次请求创建一个实例
     *        session：同一个session创建一个实例
     * @return
     */
    @Scope
    @Bean
    public Person person(){
        return new Person("张三",19);
    }

    /**
     * *@Conditional:满足条件就将给容器中注册bean
     * @return
     */
    @Conditional(MyConditional.class)
    @Bean
    public Color color(){
        return new Color("");
    }

    /**
     * FactoryBean注册bean，这个bean的类型是Monkey，getBean是名称前面加&及可获取为monkeyFactoryBean
     * @return
     */
    @Bean
    public MonkeyFactoryBean monkeyFactoryBean(){
        return new MonkeyFactoryBean();
    }
}
