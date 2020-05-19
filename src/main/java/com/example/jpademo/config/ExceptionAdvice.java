package com.example.jpademo.config;


import com.example.jpademo.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 统一异常处理
 * */
@RestControllerAdvice
public class ExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 拦截捕捉自定义异常Exception.class
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result ExceptionHandler(Exception e) {
//        Map<String,Object> dataMap=new HashMap<>();
//        List<Object> dataList=new ArrayList();
//        StackTraceElement[] stackTraceElements=e.getStackTrace();
//        for(int i=0;i<stackTraceElements.length;i++){
//            if(stackTraceElements[i].getClassName().contains("Controller")){
//                dataList.add(stackTraceElements[i]);
//                logger.info(stackTraceElements[0].getClass().toString());
//                logger.info(stackTraceElements[0].getClassName());
//                logger.info(stackTraceElements[0].getMethodName());
//                logger.info(stackTraceElements[0].getClassName());
//                logger.info(stackTraceElements[0].getFileName());
//            }
//        }
//        dataMap.put("result",e.getMessage());
//        dataMap.put("helpInfo",dataList);
//        e.printStackTrace();
//        return Result.retrunFailMsgData(dataMap);
        return Result.retrunFailMsgData("后台系统错误");
    }

    @ExceptionHandler(value = BindException.class)
    public Result ExceptionHandler(BindException e) {
//        System.out.println(e.getObjectName());;
//        System.out.println(e.getFieldError().getField());
//        System.out.println(e.getFieldError().getRejectedValue());
//        System.out.println(e.getFieldError().getDefaultMessage());

        return Result.retrunFailMsg(e.getFieldError().getDefaultMessage());
    }

}

