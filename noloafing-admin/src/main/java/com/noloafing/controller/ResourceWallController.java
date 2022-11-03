package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.Resourcewall;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.ResourcewallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/resourceWall")
public class ResourceWallController {
    @Autowired
    private ResourcewallService resourcewallService;

    //查
    @GetMapping("/list")
    public ResponseResult list(){
        List<Resourcewall> list = resourcewallService.list();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable("id")Integer id){
        Resourcewall res = resourcewallService.getById(id);
        return ResponseResult.okResult(res);
    }

    //增
    @PostMapping()
    public ResponseResult add(@RequestBody Resourcewall resourcewall){
        if (Objects.isNull(resourcewall)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        boolean save = resourcewallService.save(resourcewall);
        return save ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    //改
    @PutMapping()
    public ResponseResult edit(@RequestBody Resourcewall resourcewall){
        if (Objects.isNull(resourcewall)||Objects.isNull(resourcewall.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        boolean update = resourcewallService.updateById(resourcewall);
        return update ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    //删除
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") String id){
        String[] arr = id.split(",");
        List<Long> ids = new ArrayList<>();
        for (String str:arr) {
            ids.add(Long.parseLong(str));
        }
        boolean remove = resourcewallService.removeByIds(ids);
        return remove ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

}
