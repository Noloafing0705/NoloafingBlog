package com.noloafing.constant;

public class ArticleConstant {
    //文章正常状态
    public static final int STATUS_NORMAL = 0;
    //文章不正常状态
    public static final int STATUS_ABNORMAL = 1;
    //分页大小
    public static final int PAGE_SIZE = 10;
    //分页首页
    public static final int PAGE_HOME = 1;
    //文章置顶
    public static final String ARTICLE_TOP = "1";
    //文章评论根Id
    public static final int COMMENT_ROOT_ID = -1;
    //评论类型：文章评论
    public static final String COMMENT_TYPE_ARTICLE = "0";
    //评论类型： 友链评论
    public static final String COMMENT_TYPE_LINK = "1";
    //redis中存储浏览器的键
    public static final String REDIS_VIEW_COUNT_KEY = "Article:viewCount";
}
