package com.noloafing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.entity.ArticleTag;
import com.noloafing.mapper.ArticleTagMapper;
import com.noloafing.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-08-29 12:27:14
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

