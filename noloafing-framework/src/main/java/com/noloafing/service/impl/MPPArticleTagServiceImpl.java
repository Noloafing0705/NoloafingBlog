package com.noloafing.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.noloafing.domain.entity.ArticleTag;
import com.noloafing.mapper.MPPArticleTagMapper;
import com.noloafing.service.MPPArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class MPPArticleTagServiceImpl extends MppServiceImpl<MPPArticleTagMapper, ArticleTag> implements MPPArticleTagService {

}
