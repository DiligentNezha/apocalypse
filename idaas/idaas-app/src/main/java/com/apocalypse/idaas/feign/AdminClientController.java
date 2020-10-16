package com.apocalypse.idaas.feign;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.service.single.IdentityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdminClientController implements AdminClient {

    @Autowired
    private IdentityService identityService;

    @Override
    public Rest<BaseResponse> get(Integer id) {
        return Rest.vector("good", "hello", String.class);
    }
}
