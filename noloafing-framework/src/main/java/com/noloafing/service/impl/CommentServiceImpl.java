package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

import java.util.List;
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
        //TODO 对评论的安全性校验 例如不能为空 不能包含敏感词
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        commentMapper.insert(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> commentVoList(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        return commentVos.stream().map(commentVo -> {
            //获取评论人的昵称
            String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
            //回复评论的目标的人的userId 为 -1(即是父评论)
            // TODO 条件判断或许需要再优化
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

