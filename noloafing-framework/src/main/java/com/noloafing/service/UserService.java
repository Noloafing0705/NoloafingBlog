package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.dto.AddUserDto;
import com.noloafing.domain.dto.RegistUserDto;
import com.noloafing.domain.dto.UpdateUserDto;
import com.noloafing.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 10:13:10
 */

public interface UserService extends IService<User> {

    ResponseResult getUserInfo(Long userId);

    ResponseResult updateUserInfo(UserInfoVo user);

    ResponseResult registerUser(RegistUserDto userDto);

    ResponseResult listUsers(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult getUserDetailInfoById(Long id);

    ResponseResult updateUser(UpdateUserDto updateUserDto);

    ResponseResult deleteUserById(Long id);

    ResponseResult sendEmailCode(RegistUserDto userDto);

    ResponseResult deleteUserByIds(List<Long> ids);
}
