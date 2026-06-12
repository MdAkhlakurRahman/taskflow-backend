package com.taskflow.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.taskflow.demo.service.*.*(..))")
    public Object executionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        String className = proceedingJoinPoint.getTarget()
                                              .getClass()
                                              .getSimpleName();

        String methodName = proceedingJoinPoint.getSignature()
                                               .getName();

        long start = System.currentTimeMillis();
        log.info("Start {}.{} ",className,methodName);
        Object result;
        try{
            result = proceedingJoinPoint.proceed();
        }
        catch (Exception e) {
            log.error("Method {} failed", methodName, e);
            throw e;
        }
        finally{
            long end = System.currentTimeMillis();
            log.info("End {}.{} ",className,methodName);
            long executionTime = end - start;
            log.info("Method={}.{} Duration={}ms",className,methodName,executionTime);
        }
        return result;
    }
}