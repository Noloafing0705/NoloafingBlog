package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private String refresh;
    private UserInfoVo userInfo;

}
