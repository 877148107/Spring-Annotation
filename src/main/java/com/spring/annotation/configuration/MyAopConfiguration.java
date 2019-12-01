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
 * AOP的原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 *          主要是@EnableAspectJAutoProxy
 *          @Import(AspectJAutoProxyRegistrar.class) 利用AspectJAutoProxyRegistrar自定义给容器中注册Bean：beanDefinition
 *          给容器中注册了bean名称：internalAutoProxyCreator，bean类：org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
 *          AnnotationAwareAspectJAutoProxyCreator：注解切面的自动代理创建器
 * AnnotationAwareAspectJAutoProxyCreator的功能：
 *          父类=>AnnotationAwareAspectJAutoProxyCreator
 *              父类=>AspectJAwareAdvisorAutoProxyCreator
 *                  父类=>AbstractAdvisorAutoProxyCreator
 *                      父类=>AbstractAutoProxyCreator 实现了implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                          父类=>ProxyProcessorSupport 实现了implements Ordered, BeanClassLoaderAware, AopInfrastructureBean
 *          这个类主要实现了一个bean的后置处理器和一个BeanFactoryAware
 *          SmartInstantiationAwareBeanPostProcessor：bean的后置处理器
 *          BeanFactoryAware：可以将bean工厂传进来，自动装备beanfactory
 * 分析AbstractAutoProxyCreator关于后置处理器 装配bean前后的方法，和自动装备beanfactory的方法
 * AbstractAutoProxyCreator：
 *      AbstractAutoProxyCreator=>setBeanFactory()
 *      AbstractAutoProxyCreator=>postProcessBeforeInstantiation()
 *      AbstractAutoProxyCreator=>postProcessAfterInitialization()
 * AbstractAdvisorAutoProxyCreator:
 *      AbstractAdvisorAutoProxyCreator=>setBeanFactory()重写了父类的方法，并且调用了initBeanFactory()
 * AnnotationAwareAspectJAutoProxyCreator:
 *      AnnotationAwareAspectJAutoProxyCreator=>initBeanFactory()重写了父类的方法
 *
 * AOP流程：
 *      1.传入配置类创建IOC容器
 *      2.注册配置类，然后调用刷新方法刷新容器
 *      3.registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
 *          1).先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          2).给容器中加别的BeanPostProcessor
 *          3).优先注册实现了PriorityOrdered接口的BeanPostProcessor,
 *          4).再给容器中注册实现了Ordered接口的BeanPostProcessor,AbstractAutoProxyCreator就实现Ordered接口
 *              org.springframework.aop.config.internalAutoProxyCreator
 *          5).注册没实现优先级接口的BeanPostProcessor；
 *          6).注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中
 *             1.创建internalAutoProxyCreator的Processor（AnnotationAwareAspectJAutoProxyCreator）
 *             2.populateBean；给bean的各种属性赋值
 *             3.initializeBean：初始化bean，跟前面beanPostProcessor初始化一样
 *                 invokeAwareMethods()：处理Aware接口的方法回调
 *                 applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization（）
 *                 invokeInitMethods()；执行自定义的初始化方法
 *                 applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization（）；
 *             4.调用了AnnotationAwareAspectJAutoProxyCreator.initBeanFactory将BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功
 *                并且得到了aspectJAdvisorFactory通知工厂
 *          7).把BeanPostProcessor注册到BeanFactory中；beanFactory.addBeanPostProcessor(postProcessor)
 ***********************上面是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程************
 *AnnotationAwareAspectJAutoProxyCreator是InstantiationAwareBeanPostProcessor这种类型的后置处理器
 *      4.finishBeanFactoryInitialization：完成BeanFactory初始化工作，创建剩下的单实例bean
 *          1).遍历获取容器中所有的Bean，依次创建对象getBean(beanName)
 *              getBean(beanName)->doGetBean()->getSingleton()
 *          2).创建bean：AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，
 *                      因为继承了这个后置处理器InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation(）
 *              【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 * 				【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *              1.先从缓存中获取当前bean，如果能获取到说明bean在之前被创建过，然后就直接使用，否则再创建。创建好的bean都会放入到缓存中
 *                  后置处理器先去尝试返回一个对象：
 *                  bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                  拿到所有的后置处理器，如果是InstantiationAwareBeanPostProcessor，就执行postProcessBeforeInstantiation
 * 					if (bean != null) {
 * 						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
*                   }
 *
 *              2.创建bean：createBean()AnnotationAwareAspectJAutoProxyCreator这个会在任何bean创建之前返回bean实例
 *                  Object bean = resolveBeforeInstantiation(beanName, mbdToUse)解析BeforeInstantiation，后置处理器在此能够返回一个代理对象；
 *                  如果能返回就是使用，如果不能返回就调用doCreateBean(beanName, mbdToUse, args)去创建一个bean实例和上面创建internalAutoProxyCreator的Processor的流程一样。
 * AnnotationAwareAspectJAutoProxyCreator是InstantiationAwareBeanPostProcessor这种类型的后置处理器，其作用：
 * 关注：MyCalculate和LogAspects的bean创建
 *        1.在每个bean创建之前，调用postProcessBeforeInstantiation()
 *        2.判断当前bean是否在advisedBeans中（保存了所有需要增强bean：就是yCalculate的业务逻辑，需要切面进行切的。不能按照之前简单的bean需要增强）
 *        3.判断当前bean是不是基础类型的或者是不是切面
 *            基础类型：Advice、Pointcut、Advisor、AopInfrastructureBean
 *            切面：是不是@Aspect注解
 *        4.是否需要跳过
 *            获取候选的增强器（切面里面的通知方法）
 *            每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
 *            判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true,
 *        5.永远返回false
 *  MyCalculate创建完后：会调用postProcessAfterInitialization()
 *        return wrapIfNecessary(bean, beanName, cacheKey);如果需要的情况下进行包装
 *        1.获取当前bean的所有增强器（通知方法）
 *          1).找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
 *          2).根据List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);找到能在当前bean使用并返回能用的增强器
 *          3).给增强器排序
 *        2.获取到所有的增强器Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(),并将当前bean保存到缓存中advisedBeans.put(cacheKey, Boolean.TRUE)
 *          1).创建代理对象
 *          2).保存到代理工厂proxyFactory中
 *          3).创建代理对象：Spring自动决定
 *   				JdkDynamicAopProxy(config);jdk动态代理；
 *  				ObjenesisCglibAopProxy(config);cglib的动态代理；
 *   		4）、给容器中返回当前组件使用cglib增强了的代理对象；
 *          5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
 *          如果要执行MyCalculate的业务逻辑，就是使用cglib增强了的代理对象，调用提交保存的那些通知方法
 *目标方法MyCalculate.division的执行：
 *      容器中保存了组件的代理对象：CGLIB增强后的对象，这个对象里面包含了增强器、目标对象等详细详细
 *      1.CglibAopProxy.intercept();拦截目标方法的执行
 *      2.根据ProxyFactory对象获取将要执行目标方法的拦截器链
 *          拦截器链：把每一个通知方法包装成拦截器，利用MethodInterceptor机制
 *          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *          1).List<Object> interceptorList保存所有的拦截器，list长度为5：一个默认的和四个增强器
 *          2).遍历所有的增强器并转换成Interceptor
 *              registry.getInterceptors(advisor)
 *          3).将增强器转换成List<MethodInterceptor>
 *                  如果是MethodInterceptor，直接加入到集合中
 *  				如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor；
 *  				转换完成返回MethodInterceptor数组；
 *  			    AspectJAfterThrowingAdvice和AspectJAfterAdvice是经过转换成为了拦截器，其他三个本省就是拦截器
 *      3.如果没有拦截链直接执行目标方法
 *      4.如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等详细传入创建一个CglibMethodInvocation对象并调用。
 *      retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 *      5.拦截器链触发的过程：
 *          1).如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
 *          2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
 *  				拦截器链的机制，保证通知方法与目标方法的执行顺序；
 *  		    MethodBeforeAdviceInterceptor:
 *  		    AspectJAfterAdvice:
 *  		    AspectJAfterThrowingAdvice:
 *  		    AfterReturningAdviceInterceptor:
 * 	总结：
 * 		1）、  @EnableAspectJAutoProxy 开启AOP功能
 * 		2）、 @EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 * 		3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 * 		4）、容器的创建流程：
 * 			1）、registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
 * 			2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
 * 				1）、创建业务逻辑组件和切面组件
 * 				2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 * 				3）、组件创建完之后，判断组件是否需要增强
 * 					是：切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
 * 		5）、执行目标方法：
 * 			1）、代理对象执行目标方法
 * 			2）、CglibAopProxy.intercept()；
 * 				1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 * 				2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
 * 				3）、效果：
 * 					正常执行：前置通知-》目标方法-》后置通知-》返回通知
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
