package com.apocalypse.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import com.apocalypse.example.module.single.Demo;
import com.apocalypse.example.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 资源表(Demo)表控制层
 *
 * @author makejava
 * @since 2020-11-07 08:04:21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/demo")
@Api(value = "Demo", tags = {"Demo"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "添加", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> add(@Validated @RequestBody Demo request) {
        demoService.insertSelective(request);
        return Rest.vector("id", request.getId(), Long.class);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> update(@Validated @RequestBody Demo request) {
        Long id = request.getId();
        Demo demo = demoService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(demo)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        demoService.updateByPrimaryKeySelective(request);
        return Rest.vector("id", request.getId(), Long.class);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "详情", notes = "详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> detail(@PathVariable Long id) {
        Demo demo = demoService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(demo)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        return Rest.vector("detail", demo, Demo.class);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> delete(@PathVariable Long id) {
        demoService.deleteByPrimaryKey(id);
        return Rest.vector("deleted", true, Boolean.class);
    }

}