package com.noloafing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.entity.Resourcewall;
import com.noloafing.mapper.ResourcewallMapper;
import com.noloafing.service.ResourcewallService;
import org.springframework.stereotype.Service;

/**
 * 资源墙(Resourcewall)表服务实现类
 *
 * @author makejava
 * @since 2022-11-02 15:21:35
 */
@Service("resourcewallService")
public class ResourcewallServiceImpl extends ServiceImpl<ResourcewallMapper, Resourcewall> implements ResourcewallService {

}

