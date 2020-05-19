package com.example.jpademo.aop;

import java.lang.annotation.*;

/**
 * @author wzj
 * @date 2019-07-04 15:23
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {
}
