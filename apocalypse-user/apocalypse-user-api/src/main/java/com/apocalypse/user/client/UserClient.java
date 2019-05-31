package com.apocalypse.user.client;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.user.dto.UserRegisterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@FeignClient(name = "apocalypse-user")
public interface UserClient {

    @PostMapping("/user/user/register")
    Rest<Long> register(UserRegisterDTO register);
}
