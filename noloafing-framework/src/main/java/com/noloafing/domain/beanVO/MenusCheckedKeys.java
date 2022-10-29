package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenusCheckedKeys {
    List<MenuTreeVo> menus;
    List<Long> checkedKeys;
}
