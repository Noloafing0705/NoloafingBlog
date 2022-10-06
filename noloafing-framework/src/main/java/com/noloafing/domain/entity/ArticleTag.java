package com.noloafing.domain.entity;



import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2022-08-29 12:27:14
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nl_article_tag")
public class ArticleTag  {
    //文章id
    @MppMultiId
    @TableField("article_id")
    private Long articleId;
    //标签id
    @MppMultiId
    @TableField("tag_id")
    private Long tagId;


}

