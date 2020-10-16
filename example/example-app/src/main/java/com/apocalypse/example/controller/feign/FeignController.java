package com.apocalypse.example.controller.feign;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.feign.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private AdminClient adminClient;

    @GetMapping("/admin/get")
    public Rest<BaseResponse> get(Integer id) {
        return adminClient.get(id);
    }

}
