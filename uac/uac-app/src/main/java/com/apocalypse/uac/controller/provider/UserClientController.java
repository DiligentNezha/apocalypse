package com.apocalypse.uac.controller.provider;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.uac.client.UserClient;
import com.apocalypse.uac.dto.UserRegisterDTO;
import com.apocalypse.uac.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
