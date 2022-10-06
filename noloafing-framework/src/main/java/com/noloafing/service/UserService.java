package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 10:13:10
 */

public interface UserService extends IService<User> {

    ResponseResult getUserInfo(Long userId);

    ResponseResult updateUserInfo(UserInfoVo user);

    ResponseResult registerUser(User user);
}
