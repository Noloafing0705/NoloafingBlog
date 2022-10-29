package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRoleVo {
    private Long id;
    private String roleName;
    private String status;
}
