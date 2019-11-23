package com.spring.annotation.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *   
 *
 * @ClassName: Cat
 * =================================================
 * @Description: InitializingBean初始化接口
 *               DisposableBean销毁接口
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:32        
 * @Version: V1.0
 *     
 */
@Component
public class Cat implements InitializingBean, DisposableBean {

    public void cat(){
        System.out.println("cat 创建");
    }

    public void destroy() throws Exception {
        System.out.println("Cat destroy。。。。。。。。。");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Cat afterPropertiesSet。。。。。。。。。。");
    }
}
