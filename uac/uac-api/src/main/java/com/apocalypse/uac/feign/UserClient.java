package com.apocalypse.uac.feign;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.uac.dto.UserRegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */

@FeignClient(name = "apocalypse-uac", contextId = "user", path = "/user")
@Api(value = "用户服务【内部服务】", tags = {"用户【内部服务】"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface UserClient {

    @PostMapping("/provider/user/register")
    @ApiOperation(value = "注册【内部服务】", notes = "用户注册【内部服务】", produces = MediaType.APPLICATION_JSON_VALUE)
    Rest<Long> register(@Validated @RequestBody UserRegisterDTO register);
}
