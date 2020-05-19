package com.example.jpademo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码枚举
 * @author wzj
 * @version 1.0
 * @date 2019/10/16
 */
public enum ResultCodeEnum {

    /**
     * -1和0已经在Result中使用-1失败0成功,需做国际化的时候再用这个文件
     */
    /**
     * MQTT
     */
    PHONE_ERROR(1, "电话格式不对");


    /**
     * 值
     */
    private Integer value;
    /**
     * 名
     */
    private String name;

    ResultCodeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据value返回对应的枚举值
     *
     * @param value value
     * @return 对应的枚举值
     */
    public static ResultCodeEnum valueOf(Integer value) {
        switch (value) {
            case 1:
                return PHONE_ERROR;
            default:
                return PHONE_ERROR;
        }
    }

    /**
     * 获取枚举Map封装信息
     *
     * @return map
     */
    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ResultCodeEnum[] resultCodeEnums = ResultCodeEnum.values();
        for (ResultCodeEnum agreementType : resultCodeEnums) {
            map.put(agreementType.getValue(), agreementType.getName());
        }
        return map;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
