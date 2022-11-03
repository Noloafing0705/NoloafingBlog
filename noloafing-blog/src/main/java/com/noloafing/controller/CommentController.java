package com.noloafing.controller;

import com.noloafing.constant.ArticleConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.Comment;
import com.noloafing.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(value = "评论",tags = {"评论相关接口"})
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 文章评论列表
     */
    @GetMapping("/commentList")
    @ApiOperation(value = "文章评论列表",notes = "分页获取文章评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章ID"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult getCommentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.getCommentList(ArticleConstant.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    /**
     *
     */
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "分页获取文章评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getCommentList(ArticleConstant.COMMENT_TYPE_LINK,null,pageNum,pageSize);
    }



    /**
     * 发布和回复评论
     */
    @PostMapping()
    @ApiOperation(value = "发布和回复评论",notes = "实现评论的发布与回复")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
