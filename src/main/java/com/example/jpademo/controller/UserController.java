package com.example.jpademo.controller;

import com.example.jpademo.aop.CheckToken;
import com.example.jpademo.aop.MyLimit;
import com.example.jpademo.entity.vo.request.AddUserReqeustVo;
import com.example.jpademo.entity.vo.request.LoginUserRequestVo;
import com.example.jpademo.entity.vo.request.UpdateUsePwdRequestVo;
import com.example.jpademo.entity.vo.response.UserBaseInfoResponseVo;
import com.example.jpademo.enums.Result;
import com.example.jpademo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户相关接口
 */
@Api(tags = "1.用户相关接口",description = "添加、修改、删除、查询等接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param addUserVo
     * @return
     */
    @MyLimit(num = 1,limitTime = 10000L)
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @PostMapping("add")
    public Result add(@Valid AddUserReqeustVo addUserVo){
        return userService.add(addUserVo);
    }

    /**
     * 用户密码
     * @param loginUserVo
     * @return
     */
    @ApiOperation(value = "用户登入",notes = "用户登入")
    @GetMapping("login")
    public Result<UserBaseInfoResponseVo> add(@Valid LoginUserRequestVo loginUserVo){
        return userService.login(loginUserVo);
    }

    /**
     * 修改密码
     * @param updateUsePwdRequestVo
     * @return
     */
    @CheckToken()
    @ApiOperation(value = "修改密码",notes = "修改密码")
    @PutMapping("updatePwd")
    public Result updatePwd(@Valid UpdateUsePwdRequestVo updateUsePwdRequestVo){
        return userService.updatePwd(updateUsePwdRequestVo);
    }


//    @PutMapping("update")
//    public Result update(){
//        return null;
//    }
//
//    @GetMapping("detail")
//    public Result detail(){
//        return null;
//    }
//
//    @DeleteMapping("delete")
//    public Result delete(){
//        return null;
//    }
}
