package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.ArticleTag;
import org.springframework.stereotype.Repository;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-29 12:32:49
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

