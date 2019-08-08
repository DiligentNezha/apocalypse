package com.apocalypse.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.system.convert.RoleConvert;
import com.apocalypse.system.dto.RoleCreateDTO;
import com.apocalypse.system.model.RoleDO;
import com.apocalypse.system.service.single.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/12
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(value = "角色管理", tags = {"系统管理"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleConvert roleConvert;

    @PostMapping("/create")
    @ApiOperation(value = "新增角色", notes = "新增角色", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Integer> create(@Validated @RequestBody RoleCreateDTO roleCreateDTO) {
        RoleDO roleDO = roleConvert.convert(roleCreateDTO);
        roleService.insertSelective(roleDO);
        return Rest.ok(roleDO.getId());
    }

}
