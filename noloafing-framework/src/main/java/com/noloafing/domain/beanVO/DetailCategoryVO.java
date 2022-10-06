package com.noloafing.domain.beanVO;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailCategoryVO {
    //id
    @ExcelIgnore
    //@ExcelProperty("分类ID")
    private Long id;
    //分类名
    @ExcelProperty("分类名")
    private String name;
    //描述
    @ExcelProperty("描述")
    private String description;
    //状态
    @ExcelProperty("状态:0可用/1禁用")
    private String status;
}
