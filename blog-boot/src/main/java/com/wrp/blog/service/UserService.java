package com.wrp.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrp.blog.controller.support.LoginUserParam;
import com.wrp.blog.controller.support.RegisterUserParam;
import com.wrp.blog.controller.support.UpdateUserParam;
import com.wrp.blog.domain.User;
import com.wrp.blog.vo.UserVo;

/**
 * @author wrp
 * @since 2024-09-08 20:21
 **/
public interface UserService extends IService<User> {

    /**
     * 注册用户
     * @param registerUser 用户资料
     * @return 用户id
     */
    Long register(RegisterUserParam registerUser);

    /**
     * 登录
     * @param loginUser 登录信息
     * @return 用户信息
     */
    UserVo login(LoginUserParam loginUser);

    /**
     * 更新用户基本信息
     * @param updateUser 用户基本信息
     * @return 用户id
     */
    Long updateUser(UpdateUserParam updateUser);
}
