package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo{
    //文章Id
    private Long id;
    //文章标题
    private String title;
    //文章摘要
    private String summary;
    //文章类容
    private String content;
    //是否允许评论
    private String isComment;
    //分类Id
    private Long categoryId;
    //文章分类名
    private String categoryName;
    //访问量
    private Long viewCount;
    //文章创建时间
    private Date createTime;
}
