package com.noloafing.domain.beanVO;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.net.URL;
import java.util.Date;

@Data
@ContentRowHeight(120)
public class ExportUser {
    @ExcelProperty("ID")
    @NumberFormat(value = "#")
    private Long id;
    @ExcelIgnore
    private String avatar;
    @ColumnWidth(30)
    @ExcelProperty(value = "头像")
    private URL url;
    @ExcelProperty("用户名")
    private String userName;
    @ExcelProperty("昵称")
    private String nickName;
    @ExcelProperty("手机号码")
    private String phonenumber;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("性别(0:男/1:女)")
    private String sex;
    @ExcelProperty("状态(0:禁用/1:正常)")
    private String status;
    @ExcelProperty("创建时间")
    private Date createTime;
}
