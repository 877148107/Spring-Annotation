package com.spring.annotation.bean;

import org.springframework.beans.factory.FactoryBean;
import sun.font.TrueTypeFont;

/**
 *   
 *
 * @ClassName: MonkeyFactoryBean
 * =================================================
 * @Description: TODO
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:49        
 * @Version: V1.0
 *     
 */
public class MonkeyFactoryBean implements FactoryBean<Monkey> {

    /**
     * 返回一个对象添加到容器中
     * @return
     * @throws Exception
     */
    public Monkey getObject() throws Exception {
        return new Monkey();
    }

    public Class<?> getObjectType() {
        return Monkey.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
