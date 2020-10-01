package com.apocalypse.example.controller;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.example.dto.ExampleCreateDTO;
import com.apocalypse.example.dto.ExampleModifyDTO;
import com.apocalypse.example.dto.ExampleQueryDTO;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.service.single.ExampleService;
import com.apocalypse.example.vo.ExampleVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.ElementKind;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cache")
@Api(value = "SpringCache案例", tags = {"SpringCache案例"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CacheExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/detail/query/{id}")
    public Rest<BaseResponse> detail(@PathVariable("id") Long id) {
        ExampleDO exampleDO = exampleService.detail(id);
        return Rest.vector("content", exampleDO, ExampleDO.class);
    }

    @PostMapping("/list/query")
    public Rest<BaseResponse> list(@Validated @RequestBody ExampleQueryDTO exampleQueryDTO) {
        List<ExampleVO> exampleVOList = exampleService.listQuery(exampleQueryDTO);
        return Rest.vector("content", exampleVOList, List.class);
    }

    @PostMapping("/modify")
    public Rest<BaseResponse> modify(@Validated @RequestBody ExampleModifyDTO exampleModifyDTO) {
        ExampleDO modifiedExampleDO = exampleService.modify(exampleModifyDTO);
        return Rest.vector("content", modifiedExampleDO, ExampleDO.class);
    }

    @PostMapping("/create")
    public Rest<BaseResponse> create(@RequestBody ExampleCreateDTO exampleCreateDTO) {
        ExampleDO modifiedExampleDO = exampleService.create(exampleCreateDTO);
        return Rest.vector("content", modifiedExampleDO, ExampleDO.class);
    }

    @GetMapping("/delete/{id}")
    public Rest<BaseResponse> delete(@PathVariable("id") Long id) {
        exampleService.delete(id);
        return Rest.success();
    }

}
