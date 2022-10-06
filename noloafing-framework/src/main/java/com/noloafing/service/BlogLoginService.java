package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.User;

public interface BlogLoginService extends IService<User> {

    ResponseResult login(User user);
}
