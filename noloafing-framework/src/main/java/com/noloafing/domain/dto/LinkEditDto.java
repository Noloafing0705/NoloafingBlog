package com.noloafing.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkEditDto {
    private Long id;
    //名称
    private String name;
    //描述
    private String description;
    //图片地址
    private String logo;
    //链接
    private String address;
    //审核状态
    private String status;
}
