package com.example.springaop.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LoggerAspectJ {
    private Log log = LogFactory.getLog(LoggerAspectJ.class);

    @Before("execution(public * com.example.springaop.controller.*.*(..))")
    public void logBeforeRestCall(JoinPoint inJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Before - Start request: " + request.getRequestURI() + " - " + inJoinPoint);
    }

    @After("execution(public * com.example.springaop.controller.*.*(..))")
    public void logAfterRestCall(JoinPoint inJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("After - End request: " + request.getRequestURI() + " - " + inJoinPoint);
    }

    @AfterReturning(pointcut = "execution(public * com.example.springaop.controller.*.*(..))", returning = "inRetVal")
    public void logAfterRestCallReturnResult(JoinPoint inJoinPoint, Object inRetVal) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("AfterReturning - Value returned: " + inRetVal.toString() + " - " + inJoinPoint);
    }

    @AfterThrowing(pointcut = "execution(public * com.example.springaop.controller.*.*(..))", throwing = "error")
    public void afterThrowingAdvice(JoinPoint inJoinPoint, Throwable error) throws Throwable {
        System.out.println("AfterThrowing - Method Signature: "  + inJoinPoint.getSignature());
        System.out.println("Exception: " + error);
    }
}