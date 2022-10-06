package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
