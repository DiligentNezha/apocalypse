package com.apocalypse.system.feign;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.system.model.AdminDO;
import com.apocalypse.system.service.single.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdminClientController implements AdminClient {

    @Autowired
    private AdminService adminService;

    @Override
    public Rest<AdminDO> get(Integer id) {
        return Rest.ok(adminService.selectByPrimaryKey(id));
    }
}
