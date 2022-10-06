package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoVo {
    //文章Id
    private Long id;
    //文章标题
    private String title;
    //文章摘要
    private String summary;
    //文章缩略图
    private String thumbnail;
    //文章内容
    private String content;
    //是否允许评论
    private String isComment;
    //文章是否置顶
    private String isTop;
    //文章状态
    private String status;
    //分类Id
    private Long categoryId;
    //文章分类名
    private String categoryName;
    //访问量
    private Long viewCount;
    //文章创建时间
    private Date createTime;
}