package com.spring.annotation.configuration;

import com.spring.annotation.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @ClassName: MyProfileConfiguration
 * =================================================
 * @Description: Profile：Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *               指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册
 * 1）、加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default环境
 * 2）、写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
 * 3）、没有标注环境标识的bean在，任何环境下都是加载的；
 * 激活环节方法:1.在启动的环境变量中配置-Dspring.profiles.active=red(启动的环境名)
 *            2.在代码中激活
 *              //使用无参构造器创建一个容器
 *              AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext();
 *              //设置需要激活的环境
 *              applicationContext1.getEnvironment().setActiveProfiles("red","yellow");
 *              //注册主配置类
 *              applicationContext1.register(MyProfileConfiguration.class);
 *              //刷新启动器
 *              applicationContext1.refresh();
 *           3.写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
 *
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/24 21:45
 * @Version: V1.0
 */
@Profile("blue")
@Configuration
public class MyProfileConfiguration {

//    @Profile("default")
    @Profile("blue")
    @Bean
    public Color blueColor(){
        return new Color("Bule");
    }

    @Profile("red")
    @Bean
    public Color redColor(){
        return new  Color("Red");
    }

    @Profile("yellow")
    @Bean
    public Color yellowColor(){
        return new Color("Yellow");
    }
}
