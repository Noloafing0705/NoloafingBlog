package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.beanVO.TagVo;
import com.noloafing.domain.dto.TagListDto;
import com.noloafing.domain.entity.ArticleTag;
import com.noloafing.domain.entity.Tag;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.ArticleTagMapper;
import com.noloafing.mapper.TagMapper;
import com.noloafing.service.TagService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-08-17 15:33:17
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseResult<PageVo> listPageTags(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName,tagListDto.getName())
                    .like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        if (Objects.isNull(tagListDto)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        String name = tagListDto.getName();
        String remark = tagListDto.getRemark();
        //校验
        if (!StringUtils.hasText(name)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        if (name.contains(" ")){
            throw new SystemException(AppHttpCodeEnum.NO_SPACE);
        }
        if (name.length() >= 20){
            throw new SystemException(AppHttpCodeEnum.TOO_LONG);
        }
        if (remark !=null ){
            if(remark.length() >= 30){
                throw new SystemException(AppHttpCodeEnum.TOO_LONG);
            }
            remark.trim();
        }
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        //进行添加
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Transactional
    @Override
    public ResponseResult deleteTag(Integer id) {
        if (Objects.isNull(id)|| id<=0){
            throw new SystemException(AppHttpCodeEnum.INVALID_ID);
        }
        //删除tag标签
        tagMapper.deleteById(id);
        //删除article_tag表关联关系
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getTagId,id);
        articleTagMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        Long id = tag.getId();
        String name = tag.getName();
        String remark = tag.getRemark();
        if (Objects.isNull(id)||id<=0){
            throw new SystemException(AppHttpCodeEnum.INVALID_ID);
        }
        //校验
        if (!StringUtils.hasText(name)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        if (name.contains(" ")){
            throw new SystemException(AppHttpCodeEnum.NO_SPACE);
        }
        if (name.length() >= 14){
            throw new SystemException(AppHttpCodeEnum.TOO_LONG);
        }
        if (remark !=null ){
            if(remark.length() >= 30){
                throw new SystemException(AppHttpCodeEnum.TOO_LONG);
            }
            tag.setRemark(remark.trim());
        }
        //修改
        if(tagMapper.updateById(tag)>0){
            return ResponseResult.okResult();
        }else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult getTagById(Long id) {
        if (Objects.isNull(id)||id<=0){
            throw new SystemException(AppHttpCodeEnum.INVALID_ID);
        }
        Tag tag = tagMapper.selectById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(tag, TagVo.class));
    }

    @Transactional
    @Override
    public ResponseResult deleteTagByIds(List<Long> ids) {
        ids.forEach(id -> {
            tagMapper.deleteById(id);
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getTagId,id);
            articleTagMapper.delete(queryWrapper);
        });
        return ResponseResult.okResult();
    }


}

