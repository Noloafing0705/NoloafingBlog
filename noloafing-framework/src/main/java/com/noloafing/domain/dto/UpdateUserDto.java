package com.noloafing.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String nickName;
    private String userName;
    private String email;
    private String phonenumber;
    private String sex;
    private String status;
    private List<Long> roleIds;
}
