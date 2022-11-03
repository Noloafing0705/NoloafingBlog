package com.noloafing.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 资源墙(Resourcewall)表实体类
 *
 * @author makejava
 * @since 2022-11-02 15:21:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nl_resourcewall")
public class Resourcewall  {
    @TableId
    private Integer id;
    //资源的网址
    private String address;
    //资源说明
    private String resourceName;
    //逻辑删除
    private Integer delFlag;
    //备注
    @TableField("remark")
    private String remark;

}

