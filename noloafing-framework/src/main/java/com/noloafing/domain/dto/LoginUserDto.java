package com.noloafing.domain.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String userName;
    private String password;
    private String checkCode;
}
