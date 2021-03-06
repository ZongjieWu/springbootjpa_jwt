package com.example.jpademo.entity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "用户添加请求实体")
@Data
public class AddUserReqeustVo {
    /**
     * 用户电话
     */
    @NotEmpty(message = "用户电话不能为空")
    @Pattern(regexp = "^[150[0-9]+]{11}", message = "电话号码不正确")
    @ApiModelProperty(value = "用户电话",required = true)
    private String phone;
    /**
     * 用户名称
     */
    @NotEmpty(message = "用户名称不能为空")
    @Length(min = 2, max = 10, message = "用户名称长度在2-10个字符之间")
    @ApiModelProperty(value = "用户名称(长度在2-10个字符之间)",required = true)
    private String name;
    /**
     * 用户密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @Length(min = 6, max = 30, message = "密码长度在6-30个字符之间")
    @ApiModelProperty(value = "用户密码(密码长度在6-30个字符之间)",required = true)
    private String pwd;
}
