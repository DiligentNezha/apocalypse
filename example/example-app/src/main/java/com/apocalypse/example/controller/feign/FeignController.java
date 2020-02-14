package com.apocalypse.example.controller.feign;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.system.feign.AdminClient;
import com.apocalypse.system.model.AdminDO;
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
    public Rest<AdminDO> get(Integer id) {
        return adminClient.get(id);
    }

}
