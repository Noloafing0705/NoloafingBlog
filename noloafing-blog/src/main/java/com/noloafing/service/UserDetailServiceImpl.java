package com.noloafing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        //.eq(User::getType, SystemConstant.NORMAL_STATUS); 允许管理员登录前端
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        return new LoginUser(user,null);
    }
}
