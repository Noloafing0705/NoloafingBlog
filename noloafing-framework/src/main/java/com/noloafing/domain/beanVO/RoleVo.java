package com.noloafing.domain.beanVO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    @ExcelProperty("ID")
    private Long id;
    @ExcelProperty("角色权限字符串")
    private String roleKey;
    @ExcelProperty("名称")
    private String roleName;
    @ExcelProperty("备注")
    private String remark;
    @ExcelProperty("排序")
    private Integer roleSort;
    @ExcelProperty("状态(0:正常/1:禁用)")
    private String status;
    @ExcelProperty("创建时间")
    private Date createTime;
}
