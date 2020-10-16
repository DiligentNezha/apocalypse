package com.gkjx.saas.health.web.controller.admin;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.gkjx.common.core.api.BaseResponse;
import com.gkjx.common.core.api.Rest;
import com.gkjx.common.core.util.ContextHolder;
import com.gkjx.saas.health.system.api.request.AdminLoginRequest;
import com.gkjx.saas.health.system.api.request.PasswordModifyRequest;
import com.gkjx.saas.health.system.api.response.LoginResponse;
import com.gkjx.saas.health.system.api.response.MenuTreeResponse;
import com.gkjx.saas.health.system.api.response.StaffInfoResponse;
import com.gkjx.saas.health.system.convert.ElementConverter;
import com.gkjx.saas.health.system.convert.IdentityConverter;
import com.gkjx.saas.health.system.model.single.Element;
import com.gkjx.saas.health.system.model.single.Identity;
import com.gkjx.saas.health.system.service.single.ElementService;
import com.gkjx.saas.health.system.service.single.IdentityService;
import com.gkjx.saas.health.system.service.single.StaffService;
import com.gkjx.saas.health.web.config.security.SecurityConstants;
import com.gkjx.saas.health.web.config.security.authorization.CustomSecurityExpressionRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping
@Api(value = "认证", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminAuthController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ElementService elementService;

    @Autowired
    private ElementConverter elementConverter;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private IdentityConverter identityConverter;

    @PostMapping(SecurityConstants.ADMIN_LOGIN_PROCESSING_URL)
    @ApiOperation(value = "登录", notes = "员工登录", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<LoginResponse> fakeLogin(@Validated @RequestBody AdminLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @GetMapping(SecurityConstants.ADMIN_AUTH_LOGOUT)
    @ApiOperation(value = "注销", notes = "员工注销", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> fakeLogout() {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @GetMapping(SecurityConstants.ADMIN_MENU_TREE)
    @ApiOperation(value = "菜单树", notes = "员工登录成功获取当前员工拥有的菜单树", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<MenuTreeResponse> tree() {
        Long accountId = ContextHolder.currentAccountId();

        List<Element> elements = elementService.buildElementTreeByAccountId(accountId);
        List<MenuTreeResponse.ElementDTO> parentDTOs = elements.stream()
                .map(parent -> elementConverter.convert2ElementDTO(parent))
                .collect(Collectors.toList());
        MenuTreeResponse response = new MenuTreeResponse()
                .setElements(parentDTOs);
        return Rest.success(response);
    }

    @GetMapping("/admin/info")
    @ApiOperation(value = "员工个人信息", notes = "员工个人信息", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<StaffInfoResponse> info() {
        Identity opsIdentity = identityService.selectByPrimaryKey(ContextHolder.currentIdentityId());
        StaffInfoResponse response = identityConverter.convert2StaffInfo(opsIdentity);
        response.setPassword("******");
        response.setEmail(sensitive(response.getEmail()));
        response.setMobile(sensitive(response.getMobile()));

        return Rest.success(response);
    }

    @PostMapping("/admin/password/modify/self")
    @ApiOperation(value = "修改个人密码", notes = "修改个人密码", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "PasswordModifyResponse", properties = {
            @DynamicParameter(name = "staffId", value = "员工 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> passwordModify(@Validated @RequestBody PasswordModifyRequest request) {
        Long staffId = ContextHolder.currentStaffId();
        identityService.modifyPassword(staffId, request.getOriginalPassword(), request.getPassword());
        return Rest.vector("staffId", staffId, Long.class);
    }

    @GetMapping("/admin/email/modify/self/{email}")
    @ApiOperation(value = "修改个人邮箱", notes = "修改个人邮箱", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "EmailModifySelfResponse", properties = {
            @DynamicParameter(name = "staffId", value = "员工 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> modifySelfEmail(@PathVariable String email) {
        Long staffId = ContextHolder.currentStaffId();
        staffService.modifyEmail(staffId, email);
        return Rest.vector("staffId", staffId, Long.class);
    }

    @GetMapping("/admin/mobile/modify/self/{mobile}")
    @ApiOperation(value = "修改个人手机号", notes = "修改个人手机号", produces = MediaType.APPLICATION_JSON_VALUE)
    @DynamicResponseParameters(name = "MobileModifySelfResponse", properties = {
            @DynamicParameter(name = "staffId", value = "员工 ID", example = "1271007576242393088L", dataTypeClass = Long.class)
    })
    public Rest<BaseResponse> modifySelfMobile(@PathVariable String mobile) {
        Long staffId = ContextHolder.currentStaffId();
        staffService.modifyMobile(staffId, mobile);
        return Rest.vector("staffId", staffId, Long.class);
    }

    @GetMapping("/admin/permission/check/enable")
    @ApiOperation(value = "权限检查开启", notes = "权限检查开启", hidden = true, produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> permissionCheckEnable() {
        CustomSecurityExpressionRoot.adminPermissionCheck = true;
        return Rest.success();
    }

    @GetMapping("/admin/permission/check/disable")
    @ApiOperation(value = "权限检查关闭", notes = "权限检查关闭", hidden = true, produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> permissionCheckDisable() {
        CustomSecurityExpressionRoot.adminPermissionCheck = false;
        return Rest.success();
    }

    /**
     * 用来处理 Edge 浏览器默认请求 favicon.ico 路径使用 json 形式而产生 404
     * @return
     */
    @GetMapping("/favicon.ico")
    @ApiOperation(value = "logo", notes = "logo", hidden = true, produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> favicon() {
        return Rest.success();
    }

    private static String sensitive(String str) {
        int length = str.length();
        if (length < 3) {
            return str;
        }
        int segment = length / 3;
        int surplus = length % 3;
        String sensitiveChar = StrUtil.repeat('*', segment + surplus);
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, segment))
                .append(sensitiveChar)
                .append(str.substring((length - segment), length));
        return sb.toString();
    }

    @GetMapping(SecurityConstants.ADMIN_AUTH_CAPTCHA)
    @ApiOperation(value = "验证码", notes = "获取验证码", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "验证码", example = "fe544239-c68d-4838-a91c-6d7dae0bb88d")
    })
    public Rest<BaseResponse> captcha(@PathVariable @NotBlank String uuid) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(75, 30, 4, 4);
        String code = captcha.getCode();
        redissonClient.getBucket(StrUtil.format("auth:captcha:admin:{}", uuid))
                .setAsync(code, 5, TimeUnit.MINUTES);
        return Rest.vector("captcha", "data:image/png;base64," + captcha.getImageBase64(), String.class);
    }
}


