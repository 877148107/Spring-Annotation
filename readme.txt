AOP总结：
1.@EnableAspectJAutoProxy开启AOP功能
2.@EnableAspectJAutoProxy中导入组件@Import(AspectJAutoProxyRegistrar.class)注册Bean，AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
3.bean名称：org.springframework.aop.config.internalAutoProxyCreator，bean类：class org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
4.AnnotationAwareAspectJAutoProxyCreator是一个后置处理器。将这个后置处理器注册到容器中
5.注册后置处理器到bean工厂BeanFactory，实际上就是创建一个BeanProcessor对象放在容器中
	5.1 获取所有的后置处理，判断是否实现了相应的接口分别装载到不同的list里面
	5.2 AnnotationAwareAspectJAutoProxyCreator首先从BeanFactory中获取，如果能获取到就加入对应的list中，如果不能获取到就去实例化这个bean
		5.2.1 populateBean(beanName, mbd, instanceWrapper);给bean赋值
		5.2.2 initializeBean(beanName, exposedObject, mbd);初始化bean
		5.2.3 invokeAwareMethods(beanName, bean);处理Aware接口的方法回调
		5.2.4 applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);调用后置处理器初始化之前的方法
		5.2.5 invokeInitMethods(beanName, wrappedBean, mbd)；调用后置处理器初始化的方法
		5.2.6 applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);调用后置处理器初始化之后的方法
		5.2.7 返回初始化的后置处理器BeanProcessor
	5.3 beanFactory.addBeanPostProcessor(postProcessor)；把BeanPostProcessor注册到BeanFactory中
6.完成BeanFactory初始化工作，创建剩下的单实例bean。关注业务处理bean和切面类的bean
	6.1 Object sharedInstance = getSingleton(beanName);检查缓存中是否存在myCalculate实例bean，存在就直接获取，不存在就实例化这个bean，跟5.2逻辑一样，
	6.2 MyCalculate创建完后：会调用postProcessAfterInitialization()
		6.2.1 Object cacheKey = getCacheKey(beanClass, beanName);先从缓存中获取bean
		6.2.2 this.advisedBeans.containsKey(cacheKey)；判断当前bean是否在advisedBeans中（保存了所有需要增强bean：就是myCalculate的业务逻辑，需要切面进行切的。不能按照之前简单的bean需要增强）
		6.2.3 isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)；判断当前bean是不是基础类型的或者是不是切面，基础类型：Advice、Pointcut、Advisor、AopInfrastructureBean，切面：是不是@Aspect注解
		6.2.4 获取所有的增强器
			1). 找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
        	2). 根据List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);找到能在当前bean使用并返回能用的增强器
			3). 给增强器（通知方法）排序，后面执行增强器是会有顺序执行
			4). 获取到所有的增强器Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(),并将当前bean保存到缓存中advisedBeans.put(cacheKey, Boolean.TRUE)
			5). 创建一个代理对象，保存到代理工厂proxyFactory中：代理对象Spring自动决定；JdkDynamicAopProxy(config);jdk动态代理；ObjenesisCglibAopProxy(config);cglib的动态代理；
		
	6.3 logAspects创建
		6.7.1 跟上面6.2创建一样，但是这个类是标记了@Aspect注解是一个切面类,将这个切面类放入缓存中
		6.7.1 调用postProcessAfterInitialization()是首先从缓存中获取，上面将这个切面类放入了缓存中所以这里获取到了直接放回
7.以上步骤利用AnnotationAwareAspectJAutoProxyCreator后置处理将myCalculate和logAspects单实例bean创建完成并且闯将了代理对象放入缓存中
8.目标业务方法执行：
	8.1 CglibAopProxy.intercept();拦截目标方法的执行
	8.2 根据ProxyFactory对象获取将要执行目标方法的拦截器链.拦截器链：把每一个通知方法包装成拦截器，利用MethodInterceptor机制
           List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
		8.2.1 获取五个增强器。遍历所有的增强器并转换成Interceptor。registry.getInterceptors(advisor)
		8.2.2 这里 AspectJAfterThrowingAdvice和AspectJAfterAdvice是经过转换成为了拦截器，其他三个本身就是拦截器
		8.2.3 如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等详细传入创建一个CglibMethodInvocation对象并调用。
 		      	retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
		8.2.4 拦截器链触发的过程：
 *          1).如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
			2).这里6.2.4中第三点的时候给增强器进行了排序这里触发会按照上面排序进行触发
			MethodBeforeAdviceInterceptor:分别调用切面类对应注解的方法startLog
			AspectJAfterAdvice:分别调用切面类对应注解的方法endLog
			AspectJAfterThrowingAdvice:分别调用切面类对应注解的方法exceptionLog(
			AfterReturningAdviceInterceptor:分别调用切面类对应注解的方法returnLog
	8.3 详细执行流程见如上图
	
