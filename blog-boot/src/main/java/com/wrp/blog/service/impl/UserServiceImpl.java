package com.wrp.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrp.blog.common.UserHolder;
import com.wrp.blog.common.enums.ResultType;
import com.wrp.blog.common.exception.BusinessException;
import com.wrp.blog.controller.support.LoginUserParam;
import com.wrp.blog.controller.support.RegisterUserParam;
import com.wrp.blog.controller.support.UpdateUserParam;
import com.wrp.blog.domain.User;
import com.wrp.blog.mapper.UserMapper;
import com.wrp.blog.service.UserService;
import com.wrp.blog.util.MyBeanUtils;
import com.wrp.blog.util.RedisUtils;
import com.wrp.blog.util.jwt.JwtUtils;
import com.wrp.blog.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author wrp
 * @since 2024-09-08 21:19
 **/
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    public Long register(RegisterUserParam registerUser) {
        // 1. 校验用户名、手机号是否已被使用
        checkUsernameAndPhone(registerUser.getUsername(), registerUser.getPhone());
        // 2. 注册用户
        User user = doRegister(registerUser);
        return user.getId();
    }

    private void checkUsernameAndPhone(String username, String phone) {
        if(StringUtils.hasText(username) && getByUsername(username) != null) {
            throw BusinessException.of(ResultType.U_USERNAME_USED);
        }
        if(StringUtils.hasText(phone) && getByPhone(phone) != null) {
            throw BusinessException.of(ResultType.U_PHONE_USED);
        }
    }

    private User getByUsername(String username) {
        Assert.notNull(username, "username is null");
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }

    private User getByPhone(String phone) {
        Assert.notNull(phone, "phone is null");
        return getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));
    }

    private User doRegister(RegisterUserParam registerUser) {
        User user = new User();
        BeanUtils.copyProperties(registerUser, user);
        save(user);
        return user;
    }

    @Override
    public UserVo login(LoginUserParam loginUser) {
        User user = getByUsername(loginUser.getUsername());
        if(user == null) {
            throw BusinessException.of(ResultType.U_USERNAME_NOT_FOUND);
        }
        if(!Objects.equals(user.getPassword(),loginUser.getPassword())) {
            throw BusinessException.of(ResultType.U_PASSWORD_ERROR);
        }
        UserVo userVo = convert(user);
        userVo.setToken(jwtUtils.createJWT(user));
        // 将用户信息缓存到redis
        redisUtils.set(loginUser.getUsername(), user);
        return userVo;
    }

    private UserVo convert(User user) {
        if(user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public Long updateUser(UpdateUserParam updateUser) {
        User user = UserHolder.getUser();
        checkUsernameAndPhone(updateUser.getUsername(), updateUser.getPhone());

        doUpdateUser(user, updateUser);
        return user.getId();
    }

    private void doUpdateUser(User user, UpdateUserParam updateUser) {
        MyBeanUtils.copyIgnoreNull(updateUser, user);
        updateById(user);
    }
}
