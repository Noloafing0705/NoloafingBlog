package com.noloafing.service;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult sendAdminEmailCode();
}
