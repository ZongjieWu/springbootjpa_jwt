package com.example.jpademo.entity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "用户基本信息实体响应数据")
@Data
public class UserBaseInfoResponseVo {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",required = true)
    private Long id;
    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话",required = true)
    private String phone;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称",required = true)
    private String name;
    /**
     * 用户token
     */
    @ApiModelProperty(value = "用户token",required = true)
    private String token;
}
