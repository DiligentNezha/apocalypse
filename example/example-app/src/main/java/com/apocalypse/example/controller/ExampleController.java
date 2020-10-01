package com.apocalypse.example.controller;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.constraints.EnumMatch;
import com.apocalypse.common.core.enums.GenderEnum;
import com.apocalypse.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/example")
@Api(value = "Example", tags = {"Example"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ExampleController {

    @PostMapping("/enum/post")
    @ApiOperation(value = "枚举校验(RequestBody)", notes = "扩展枚举校验", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> enumPostValidate(@Validated @RequestBody EnumRequest request) {
        return Rest.vector("content", request, EnumRequest.class);
    }

    @PostMapping("/enum/get")
    @ApiOperation(value = "枚举校验(RequestParam)", notes = "扩展枚举校验", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> enumGetValidate(@Valid @EnumMatch(GenderEnum.class) @RequestParam("gender") String gender) {
        if (true) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, 1);
        }
        return Rest.vector("content", gender, EnumRequest.class);
    }

//    @Autowired
//    private NodeServiceClient nodeServiceClient;

    @PostMapping("/sidecar/node")
    @ApiOperation(value = "异构 node", notes = "异构 node 测试", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> enumGetValidate() {
//        Rest hello = nodeServiceClient.hello();
//        log.info(JSON.toJSONString(hello));
        return Rest.success();
    }

    @Getter
    @Setter
    public static class EnumRequest {

        @EnumMatch(GenderEnum.class)
        @ApiModelProperty(value = "性别索引", example = "1")
        private Integer genderIndex;

        @EnumMatch(GenderEnum.class)
        private String gender;
    }

}
