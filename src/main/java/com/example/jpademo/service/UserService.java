package com.example.jpademo.service;


import com.example.jpademo.entity.vo.request.AddUserReqeustVo;
import com.example.jpademo.entity.vo.request.LoginUserRequestVo;
import com.example.jpademo.entity.vo.request.UpdateUsePwdRequestVo;
import com.example.jpademo.entity.vo.response.UserBaseInfoResponseVo;
import com.example.jpademo.enums.Result;

public interface UserService {
    /**
     * 用户注册
     * @param addUserVo
     * @return
     */
    Result add(AddUserReqeustVo addUserVo);

    /**
     * 用户登入
     * @param loginUserVo
     * @return
     */
    Result<UserBaseInfoResponseVo> login(LoginUserRequestVo loginUserVo);

    /**
     * 修改密码
     * @param updateUsePwdRequestVo
     * @return
     */
    Result updatePwd(UpdateUsePwdRequestVo updateUsePwdRequestVo);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Result<UserBaseInfoResponseVo> findById(Long id);
//    Result update(AddUserReqeustVo addUserVo);
//    Result delete(AddUserReqeustVo addUserVo);
//    Result detail(AddUserReqeustVo addUserVo);
}
