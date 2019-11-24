package com.spring.annotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @ClassName: LogAspects
 * =================================================
 * @Description: AOP切面类
 *  @Aspect告诉Spring这是一个切面类
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/24 22:20
 * @Version: V1.0
 */
@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点
     * 如果在本类引入的话直接写pointCut()即可
     */
    @Pointcut("execution(public int com.spring.annotation.aop.MyCalculate.*(..))")
    public void pointCut(){

    }

    /**
     * MyCalculate.division运行之前的监控
     * 前置通知
     */
    @Before("pointCut()")
    public void startLog(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println(signature.getName()+"开始。。。。。。参数："+ Arrays.asList(args));
    }

    /**
     * MyCalculate.division运行结束的监控
     * 后置通知
     */
    @After("pointCut()")
    public void endLog(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName()+"结束。。。。。。");
    }

    /**
     * MyCalculate.division运行返回监控
     * 返回通知
     */
    @AfterReturning(value = "pointCut()",returning = "reslut")
    public void returnLog(JoinPoint joinPoint,Object reslut){
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName()+"MyCalculate.division返回结果。。。。。。"+reslut);
    }

    /**
     * 运行异常的监控
     * 异常通知
     */
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void exceptionLog(JoinPoint joinPoint,Exception exception){
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName()+"异常。。。。。。"+exception);
    }
}
