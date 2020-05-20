package com.example.jpademo.service.impl;

import com.example.jpademo.dao.UserDao;
import com.example.jpademo.entity.User;
import com.example.jpademo.entity.vo.request.AddUserReqeustVo;
import com.example.jpademo.entity.vo.request.LoginUserRequestVo;
import com.example.jpademo.entity.vo.request.UpdateUsePwdRequestVo;
import com.example.jpademo.entity.vo.response.UserBaseInfoResponseVo;
import com.example.jpademo.enums.Result;
import com.example.jpademo.service.UserService;
import com.example.jpademo.util.AESUtil;
import com.example.jpademo.util.BeanCopyTools;
import com.example.jpademo.util.JWTUtils;
import com.example.jpademo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    public static final String AES_KEY = "t89zsda45fdf4wa5";

    @Autowired
    private UserDao userDao;

    /**
     *用户注册
     * @param addUserVo
     * @return
     */
    @Override
    public Result add(AddUserReqeustVo addUserVo) {
        User user= BeanCopyTools.copy(addUserVo,User.class);

        /**
         * 指定查询条件
         */
        Specification<User> spec = new Specification<User>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("phone").as(String.class), user.getPhone()));

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        /**
         * 查询用户
         */
        Optional<User> optionalT = userDao.findOne(spec);
        User resUser=optionalT.isPresent() ? optionalT.get(): null;

        /**
         * 当前手机号不存在才可以注册
         */
        if(resUser==null){
            user.setPwd(MD5Util.getMD5(AESUtil.encode(user.getPwd(), AES_KEY)));
            user.setCreateDate(new Date());
            user.setModifyDate(new Date());
            userDao.save(user);
            return Result.retrunSucess();
        }else{
            return Result.retrunSucessMsg("该手机号已经注册过了");
        }
    }

    /**
     * 登入
     * @param loginUserVo
     * @return
     */
    @Override
    public Result login(LoginUserRequestVo loginUserVo) {
        User user= BeanCopyTools.copy(loginUserVo,User.class);

        /**
         * 指定查询条件
         */
        Specification<User> spec = new Specification<User>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("phone").as(String.class), user.getPhone()));

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        /**
         * 查询用户
         */
        Optional<User> optionalT = userDao.findOne(spec);
        User resUser=optionalT.isPresent() ? optionalT.get(): null;

        if(resUser==null){
            return Result.retrunFailMsg("此账号不存在");
        }
        if(!MD5Util.getMD5(AESUtil.encode(user.getPwd(), AES_KEY)).equals(resUser.getPwd())){
            return Result.retrunFailMsg("密码错误");
        }


        /**
         * 设置token
         */
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("loginId", resUser.getId());
        //86400000是过期时间  24小时
        String jwt=null;
        try {
            jwt = JWTUtils.createJWT("jwt", resUser.getPhone(), 120000,payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserBaseInfoResponseVo ubir= BeanCopyTools.copy(resUser,UserBaseInfoResponseVo.class);
        ubir.setToken(jwt);

        return Result.retrunSucessMsgData(ubir);
    }

    /**
     * 修改密码
     * @param updateUsePwdRequestVo
     * @return
     */
    @Override
    public Result updatePwd(UpdateUsePwdRequestVo updateUsePwdRequestVo) {
        User user= BeanCopyTools.copy(updateUsePwdRequestVo,User.class);

        /**
         * 指定查询条件
         */
        Specification<User> spec = new Specification<User>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("phone").as(String.class), user.getPhone()));

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        /**
         * 查询用户
         */
        Optional<User> optionalT = userDao.findOne(spec);
        User resUser=optionalT.isPresent() ? optionalT.get(): null;

        if(resUser==null){
            return Result.retrunFailMsg("此账号不存在");
        }
        if(!MD5Util.getMD5(AESUtil.encode(user.getPwd(), AES_KEY)).equals(resUser.getPwd())){
            return Result.retrunFailMsg("密码错误");
        }

        /**
         * 设置新密码
         */
        resUser.setPwd(MD5Util.getMD5(AESUtil.encode(updateUsePwdRequestVo.getNewPwd(), AES_KEY)));
        resUser.setModifyDate(new Date());
        userDao.saveAndFlush(resUser);
        return Result.retrunSucess();
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    @Override
    public Result<UserBaseInfoResponseVo> findById(Long id) {
        Optional<User> optionalT = userDao.findById(id);
        User resUser=optionalT.isPresent() ? optionalT.get(): null;
        UserBaseInfoResponseVo userBaseInfoResponseVo= BeanCopyTools.copy(resUser,UserBaseInfoResponseVo.class);
        return Result.retrunSucessMsgData(userBaseInfoResponseVo);
    }
}
