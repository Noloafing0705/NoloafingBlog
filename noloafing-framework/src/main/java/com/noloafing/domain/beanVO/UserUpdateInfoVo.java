package com.noloafing.domain.beanVO;

import com.noloafing.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改User时相关的role roleIds 以及 userInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateInfoVo {
    private List<Long> roleIds;
    private List<Role> roles;
    UserDetailInfoVo user;
}
