package com.apocalypse.user.controller.provider;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.user.client.UserClient;
import com.apocalypse.user.dto.UserRegisterDTO;
import com.apocalypse.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserClientController implements UserClient {

    @Autowired
    private UserService userService;

    @Override
    public Rest<Long> register(UserRegisterDTO register) {
        Rest<Long> rest = new Rest<>();
        Long userId = userService.register(register);
        rest.setData(userId);
        return rest;
    }

}
