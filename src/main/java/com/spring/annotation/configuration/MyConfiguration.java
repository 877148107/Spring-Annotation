package com.spring.annotation.configuration;

import com.spring.annotation.bean.Color;
import com.spring.annotation.bean.MonkeyFactoryBean;
import com.spring.annotation.bean.Person;
import com.spring.annotation.conditional.MyConditional;
import com.spring.annotation.impor.MyDefinitionRegistrar;
import com.spring.annotation.impor.MyImportSelector;
import com.spring.annotation.service.annotationService;
import org.springframework.context.annotation.*;


/**
 * ??
 *
 * @ClassName: Color
 * =================================================
 * @Description: Springע������
 *  *@Configuration:����Spring����һ��������,�൱��ԭ����xml�����ļ�
 *  *@ComponentScan:ָ��Ҫɨ��İ���������������ע��������ӵ�������
 *  *@Import:���ٸ������е���һ�����,�����оͻ��Զ�ע����������idĬ����ȫ����
 *  *@MyImportSelector:�Զ����߼�������Ҫ��������
 *  *@MyDefinitionRegistrar:�Զ����ֶ�ע��bean��������
 *  *��������FactoryBeanע��bean��1.Ĭ�ϻ�ȡ�����ǹ���bean����getObject�����Ķ���2.Ҫ��ȡ����Bean����������Ҫ��idǰ���һ��&
 * 	 * 			&colorFactoryBean
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/20 23:14????????
 * @Version: V1.0
 * ????
 */
@Configuration
@ComponentScan(value = {"com.spring.annotation.dao"})
@Import({annotationService.class, MyImportSelector.class, MyDefinitionRegistrar.class})
public class MyConfiguration {

    /**
     * *@Scope:����������
     *    *@Scope("prototype")prototype����ʵ���ģ�ioc��������������ȥ���÷�������������������С�
     *                                   ÿ�λ�ȡ��ʱ��Ż���÷�����������
     *    *@Scope("singleton")singleton����ʵ���ģ�Ĭ��ֵ����ioc������������÷�����������ŵ�ioc�����С�
     *         			�Ժ�ÿ�λ�ȡ����ֱ�Ӵ�������map.get()�����ã�
     *        request��ͬһ�����󴴽�һ��ʵ��
     *        session��ͬһ��session����һ��ʵ��
     * @return
     */
    @Scope
    @Bean
    public Person person(){
        return new Person("����",19);
    }

    /**
     * *@Conditional:���������ͽ���������ע��bean
     * @return
     */
    @Conditional(MyConditional.class)
    @Bean
    public Color color(){
        return new Color();
    }

    /**
     * FactoryBeanע��bean�����bean��������Monkey��getBean������ǰ���&���ɻ�ȡΪmonkeyFactoryBean
     * @return
     */
    @Bean
    public MonkeyFactoryBean monkeyFactoryBean(){
        return new MonkeyFactoryBean();
    }
}
