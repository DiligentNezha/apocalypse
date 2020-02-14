package com.apocalypse.system.feign;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.system.model.AdminDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "apocalypse-system", path = "/system-api")
public interface AdminClient {

    @PostMapping("/provider/admin/get")
    Rest<AdminDO> get(@RequestBody Integer id);

}
