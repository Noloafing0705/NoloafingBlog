package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.LinkConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.LinkListVo;
import com.noloafing.domain.beanVO.LinkVo;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.entity.Link;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.mapper.LinkMapper;
import com.noloafing.service.LinkService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-08-10 22:00:22
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private LinkMapper linkMapper;


    @Override
    public ResponseResult getLinks() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, LinkConstant.STATUS_NORMAL);
        List<Link> links = linkMapper.selectList(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult pageLink(Integer pageNum, Integer pageSize, String name, String status) {
        if (Objects.isNull(pageNum)||Objects.isNull(pageSize)||pageSize <= 0||pageNum<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Link::getName,name)
                    .eq(StringUtils.hasText(status), Link::getStatus,status);
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Link> records = page.getRecords();
        List<LinkListVo> listVos = BeanCopyUtils.copyBeanList(records, LinkListVo.class);
        return ResponseResult.okResult(new PageVo(listVos, page.getTotal()));
    }
}

