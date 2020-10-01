package com.apocalypse.idaas.feign;

import com.apocalypse.common.core.api.Rest;
import com.apocalypse.idaas.model.AdminDO;
import com.apocalypse.idaas.service.single.AdminService;
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
        return Rest.success(adminService.selectByPrimaryKey(id));
    }
}
