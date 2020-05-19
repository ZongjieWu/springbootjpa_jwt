package com.example.jpademo.aop;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wzj
 * @version 1.0
 * @date 2019/12/11
 */
@Component
public class LimitByMap {
    //全局MAP key为userId+URI  value为CountParam 统计对象 记录时间和次数
    public static ConcurrentHashMap<String, CountParam> cacheMap = new ConcurrentHashMap<>();

    /**
     *
     * @param key	自定义key userId+URI
     * @param num	自定义限制请求次数 超出次数后限制访问
     * @param limitTime	自定义限制时间，限制时间内限制访问次数
     */
    public Boolean limitRequest(String key,int num,Long limitTime){
        //1通过key获取到countPatam对象
        CountParam countParam = cacheMap.get(key);
        //2判断对象是否为空，如果为空则代表第一次访问 如果不为空则代表之前请求过
        if(countParam!=null){
            //3获取时间，用当前时间减去最后一次访问时间，如果规定时间则清零访问次数，小于则累加计数
            Long time = System.currentTimeMillis()-countParam.getTime();
            int currentCount = countParam.getCount();
            if(time<limitTime){
                //4如果累计次数大于10，证明用户在限制时间内访问过多
                if(currentCount>=num){
//                    System.out.println("访问超出限制次数");
                    return true;
                }else{
                    countParam.setCount(++currentCount);
                }
            }else{
                countParam.setCount(1);
            }
            System.out.println(countParam.getCount());
            countParam.setTime(System.currentTimeMillis());
        }else{
            countParam = new CountParam();
            countParam.setCount(1);
            countParam.setTime(System.currentTimeMillis());
        }
        cacheMap.put(key, countParam);
        return false;
    }

}

