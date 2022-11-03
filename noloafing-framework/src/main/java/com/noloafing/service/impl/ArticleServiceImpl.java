package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.ArticleConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.*;
import com.noloafing.domain.entity.Article;
import com.noloafing.domain.entity.ArticleTag;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.*;
import com.noloafing.service.ArticleService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Resource
    private MPPArticleTagMapper mppArticleTagMapper;

    @Override
    public ResponseResult getHotArticleLIst() {
        //条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询正常的文章
        queryWrapper.eq(Article::getStatus, ArticleConstant.STATUS_NORMAL)
                    .orderByDesc(Article::getViewCount);//按照浏览量递减
        //按分页取出第一页中的十条数据
        Page<Article> page = new Page<>(ArticleConstant.PAGE_HOME, ArticleConstant.PAGE_SIZE);
        //执行按条件分页查询
        page(page, queryWrapper);
        //获取数据
        List<Article> articles = page.getRecords();
        //从redis获取浏览量
        //articles = articles.stream().map(article -> redisViewCount(article)).collect(Collectors.toList());
        //使用Vo对数据的必要信息封装
        List<HotArticleVo> hotArticles = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(hotArticles);
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果传入了正确的分类ID，就进行查询对应分类的文章
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //查询分类下正常的文章（如果没有传入ID那么就是查询所有的文章）
        queryWrapper.eq(Article::getStatus,ArticleConstant.STATUS_NORMAL)
                    .orderByDesc(Article::getIsTop)//根据置顶排序
                    .orderByDesc(Article::getCreateTime);
        //分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        List<Article> articles = page(page, queryWrapper).getRecords();
        //封装分类名（从redis获取浏览量）
        articles = articles.stream().map(article -> redisViewCount(article).setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName())
        ).collect(Collectors.toList());
        //Vo封装
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        if (Objects.nonNull(id) && id>0){
            Article article = articleMapper.selectById(id);
            if (article != null){
                //从redis中获取viewCount
                article = redisViewCount(article);
                String categoryName = categoryMapper.selectById(article.getCategoryId()).getName();
                ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
                articleDetailVo.setCategoryName(categoryName);
                return ResponseResult.okResult(articleDetailVo);
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //执行一次浏览量加一
        redisCache.incrementCacheMapValue(ArticleConstant.REDIS_VIEW_COUNT_KEY, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult saveArticle(Article article) {
        try {
            int insert = articleMapper.insert(article);
            article.getTags().stream().forEach(tagId -> articleTagMapper.insert(new ArticleTag(article.getId(), Long.valueOf(tagId))));
            return ResponseResult.okResult();
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    //返回所有的文章
    @Override
    public ResponseResult getAllArticles(Integer pageNum, Integer pageSize, String title, String summary) {
        if(Objects.isNull(pageNum)||Objects.isNull(pageSize)||pageNum<0||pageSize<0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(title), Article::getTitle,title )
                    .like(StringUtils.hasText(summary), Article::getSummary,summary);

        Page<Article> page = new Page<>(pageNum, pageSize);
        List<Article> records = page(page, queryWrapper).getRecords();
        List<ArticleInfoVo> articleInfoVos = BeanCopyUtils.copyBeanList(records, ArticleInfoVo.class);
        PageVo pageVo = new PageVo(articleInfoVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Transactional
    @Override
    public ResponseResult getArticleInfo(Long id) {
        Article article = articleMapper.selectById(id);
        List<String> tags = tagMapper.getTagsByArticleId(id);
        ArticleUpdateVo updateVo = BeanCopyUtils.copyBean(article, ArticleUpdateVo.class);
        updateVo.setTags(tags);
        return ResponseResult.okResult(updateVo);
    }

    @Transactional
    @Override
    public ResponseResult updateArticle(ArticleUpdateVo articleUpdateVo) {
        if (Objects.isNull(articleUpdateVo) || articleUpdateVo.getId()==null){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Article article = BeanCopyUtils.copyBean(articleUpdateVo, Article.class);
        updateById(article);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagMapper.delete(queryWrapper);
        article.getTags().stream().forEach(s -> mppArticleTagMapper.insert(new ArticleTag(article.getId(), Long.parseLong(s))));
        return ResponseResult.okResult();
    }



    private Article redisViewCount(Article article){
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(ArticleConstant.REDIS_VIEW_COUNT_KEY, article.getId().toString());
        //当有新的博文被发布，这边刷新时不会存在新文章的浏览量的redis缓存,viewCount == null
        if (viewCount == null){
            //新发布文章的浏览量存入redis 新文章viewCount默认为0
            redisCache.setCacheMapValue(ArticleConstant.REDIS_VIEW_COUNT_KEY, article.getId().toString(), article.getViewCount().intValue());
        }else {
            article.setViewCount(viewCount.longValue());
        }
        return article;
    }


}
