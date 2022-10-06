package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    //友链Id
    private Long id;
    //友链名称
    private String name;
    //图标地址
    private String logo;
    //友链描述
    private String description;
    //网站地址
    private String address;
}
