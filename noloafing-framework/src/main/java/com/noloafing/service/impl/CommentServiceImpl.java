package com.noloafing.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.baiduAI.BaiduAICheck;
import com.noloafing.baiduAI.HttpUtil;
import com.noloafing.baiduAI.TextCheckReturn;
import com.noloafing.constant.ArticleConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.CommentVo;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.entity.Comment;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.CommentMapper;
import com.noloafing.mapper.UserMapper;
import com.noloafing.service.CommentService;
import com.noloafing.utils.BeanCopyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-08-12 15:19:00
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;





    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //根据类型查询文章评论、友链评论
        queryWrapper.eq(ArticleConstant.COMMENT_TYPE_ARTICLE.equals(commentType),Comment::getArticleId, articleId)
                .eq(Comment::getRootId, ArticleConstant.COMMENT_ROOT_ID)
                .eq(Comment::getType,commentType)
                .orderByAsc(Comment::getCreateTime);
        //实现分页
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        List<Comment> comments = page.getRecords();
        //获取用户头像
        //vo封装
        List<CommentVo> commentVos = commentVoList(comments);
        //封装子评论
        for (CommentVo commentVo : commentVos) {
            LambdaQueryWrapper<Comment> childrenQueryWrapper = new LambdaQueryWrapper<>();
            childrenQueryWrapper
                    .eq(Comment::getRootId, commentVo.getId())
                    .orderByAsc(Comment::getCreateTime);
            commentVo.setChildren(commentVoList(list(childrenQueryWrapper)));
        }
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }


    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            return  ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        String text =comment.getContent();
        try {
            String access_token = BaiduAICheck.getAuth();
            //设置请求的编码
            String param = "text=" + URLEncoder.encode(text, "UTF-8");
            //调用文本审核接口并取得结果
            String result = HttpUtil.post(BaiduAICheck.CHECK_TEXT_URL, access_token, param);
            TextCheckReturn checkReturn = JSON.parseObject(result, TextCheckReturn.class);
            //如果合规
            if (checkReturn.getConclusionType()!=null && checkReturn.getConclusionType().equals(1)){
                commentMapper.insert(comment);
                return ResponseResult.okResult();
            }
            //如果疑似，不合规，
            return ResponseResult.errorResult(AppHttpCodeEnum.COMMENT_INCLUDE_BAD_COTENT);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(AppHttpCodeEnum.COMMENT_CONTENT_CHECKED_FALSE);
        }

    }

    private List<CommentVo> commentVoList(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        return commentVos.stream().map(commentVo -> {
            //获取评论人的昵称
            String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
            //回复评论的目标的人的userId 为 -1(即是父评论)
            if (commentVo.getToCommentUserId() == ArticleConstant.COMMENT_ROOT_ID) {
                return commentVo.setUsername(nickName);
            }
            return commentVo.setUsername(nickName)
                    .setToCommentUserName(userMapper.selectById(commentVo.getToCommentUserId()).getNickName());
        }).collect(Collectors.toList());
    }

    private void childrenCommentVoList(List<CommentVo> commentVos) {


        /*comments.stream().map(comment -> {
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getArticleId,comment.getArticleId())
                    .eq(Comment::getRootId,comment.getId());
            return commentVoList(list(queryWrapper));
        });*/
    }

}

