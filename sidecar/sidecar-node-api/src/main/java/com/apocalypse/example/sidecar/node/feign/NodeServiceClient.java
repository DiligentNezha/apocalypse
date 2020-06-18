package com.apocalypse.example.sidecar.node.feign;

import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/18
 */
@FeignClient(name = "sidecar-node")
@Api(value = "异构 node", tags = {"异构 node"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface NodeServiceClient {

    @PostMapping("/hello")
    @ApiOperation(value = "hello", notes = "hello node", produces = MediaType.APPLICATION_JSON_VALUE)
    Rest hello();
}
