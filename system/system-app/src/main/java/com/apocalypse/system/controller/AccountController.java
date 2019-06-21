package com.apocalypse.system.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.util.ServiceExceptionUtil;
import com.apocalypse.system.convert.AdminConvert;
import com.apocalypse.system.dto.LoginDTO;
import com.apocalypse.system.enums.SystemErrorCodeEnum;
import com.apocalypse.system.model.AdminDO;
import com.apocalypse.system.service.single.AdminService;
import com.apocalypse.system.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.weekend.Weekend;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/12
 */
@Slf4j
@RestController
@RequestMapping("/account")
@Api(value = "账号服务", tags = {"公共服务"}, consumes = "application/json")
public class AccountController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminConvert adminConvert;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录", produces = "application/json")
    public Rest<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        Weekend<AdminDO> weekend = Weekend.of(AdminDO.class);
        weekend.weekendCriteria()
                .andEqualTo(AdminDO::getMail, loginDTO.getMail())
                .andEqualTo(AdminDO::getPassword, loginDTO.getPassword());
        AdminDO adminDO = adminService.selectOneByExample(weekend);
        if (ObjectUtil.isNull(adminDO)) {
            throw ServiceExceptionUtil.exception(SystemErrorCodeEnum.ADMIN_USERNAME_NOT_REGISTERED);
        }
        LoginVO loginVO = adminConvert.convert(adminDO);
        loginVO.setSessionId(IdUtil.simpleUUID());
        return Rest.ok(loginVO);
    }

}
