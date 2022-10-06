package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-08-10 22:00:22
 */
public interface LinkService extends IService<Link> {

    ResponseResult getLinks();

    ResponseResult pageLink(Integer pageNum, Integer pageSize, String name, String status);
}
