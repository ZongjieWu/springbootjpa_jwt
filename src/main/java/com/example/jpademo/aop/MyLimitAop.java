package com.example.jpademo.aop;

import com.example.jpademo.enums.Result;
import com.example.jpademo.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author wzj
 * @version 1.0
 * @date 2019/12/11
 */
@Aspect
@Component
public class MyLimitAop {
    @Autowired
    private LimitByMap limitMap;


    @Around(value = "execution (* com.example.jpademo.controller..*.*(..)) && @annotation(com.example.jpademo.aop.MyLimit)")
    @Order(2)
    public Object limit(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if(method.isAnnotationPresent(MyLimit.class)){
            MyLimit myLimit = method.getAnnotation(MyLimit.class);
            long limitTime = myLimit.limitTime();
            int num=myLimit.num();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String requestURI = request.getRequestURI();
            String userID = IPUtils.getIpAddr(request);
            String key = userID+requestURI;
            Boolean ifOver=limitMap.limitRequest(key,num,limitTime);

            if(ifOver){
            //超过请求限制
                return Result.retrunFailMsg(limitTime/1000+"秒内只能请求"+num+"次");
            }else{
                return joinPoint.proceed();
            }
        }
        return joinPoint.proceed();
    }
}

