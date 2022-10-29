package com.noloafing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.mapper.MenuMapper;
import com.noloafing.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username)
                    .eq(User::getType, SystemConstant.TYPE_ADMIN);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //用户存在 查询权限信息 后台管理需要权限查询
        List<String> perms = menuMapper.getPartMenus(user.getId());
        return new LoginUser(user, perms);
    }




}
