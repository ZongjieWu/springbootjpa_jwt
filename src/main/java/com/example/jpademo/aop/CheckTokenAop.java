package com.example.jpademo.aop;

import com.example.jpademo.entity.vo.response.UserBaseInfoResponseVo;
import com.example.jpademo.enums.Result;
import com.example.jpademo.service.UserService;
import com.example.jpademo.util.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author wzj
 * @date 2019-07-03 16:18
 */
@Component
@Aspect
public class CheckTokenAop {

    @Autowired
    private UserService userService;

    /**
     * 拦截所有有关权限的请求
     * 传入部门id 通过当前用户id 去查看所有权限如果发现没对应的权限则返回
     */
    @Around(value = "execution (* com.example.jpademo.controller..*.*(..)) && @annotation(com.example.jpademo.aop.CheckToken)")
    @Order(2)
    public Object getFunctionTreeInfoVoAop(ProceedingJoinPoint pjp) throws Throwable {
        //拦截方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Type type = method.getAnnotatedReturnType().getType();
        CheckToken lock = method.getAnnotation(CheckToken.class);
        if (lock == null) {
            return pjp.proceed();
        }

        //使用环绕增强
        //获取request对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String jwt=request.getHeader("token");
        if (null==jwt||"".equals(jwt)) {
            return Result.retrunFailMsg("请先登录!!!");
        }

        Claims c=null;
        try {
            c= JWTUtils.parseJWT(jwt);
        }catch (ExpiredJwtException e){
            return Result.retrunFailMsg("登入超时,请重新登入(暂2分钟后超时)");
        }

        //验证身份
        if(c.get("loginId")==null){
            return Result.retrunFailMsg("请联系管理员获取账号1!!!");
        }
        UserBaseInfoResponseVo ubir=userService.findById(Long.valueOf(c.get("loginId").toString())).getData();
        if(ubir==null){
            return Result.retrunFailMsg("请联系管理员获取账号!!!");
        }
        //验证时间
        if(System.currentTimeMillis()<=Long.valueOf(c.get("exp").toString())){
            System.out.println("请求超时哦！");
            return Result.retrunFailMsg("登入超时,请重新登入");
        }
        return pjp.proceed();
    }

}
