package com.example.jpademo.aop;

import java.lang.annotation.*;

/**
 * @author wzj
 * @version 1.0
 * @date 2019/12/11
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLimit {
    long limitTime() default 60000;
    int num() default 50;
}
