package com.apocalypse.example.controller;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.example.dto.ExampleCreateDTO;
import com.apocalypse.example.dto.ExampleModifyDTO;
import com.apocalypse.example.dto.ExampleQueryDTO;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.service.single.ExampleService;
import com.apocalypse.example.vo.ExampleVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cache")
@Api(value = "SpringCache案例", tags = {"SpringCache案例"}, consumes = "application/json")
public class CacheExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/detail/query/{id}")
    public Rest<ExampleDO> detail(@PathVariable("id") Long id) {
        ExampleDO exampleDO = exampleService.detail(id);
        return Rest.ok(exampleDO);
    }

    @PostMapping("/list/query")
    public Rest<List<ExampleVO>> list(@Validated @RequestBody ExampleQueryDTO exampleQueryDTO) {
        List<ExampleVO> exampleVOList = exampleService.listQuery(exampleQueryDTO);
        return Rest.ok(exampleVOList);
    }

    @PostMapping("/modify")
    public Rest<ExampleDO> modify(@Validated @RequestBody ExampleModifyDTO exampleModifyDTO) {
        ExampleDO modifiedExampleDO = exampleService.modify(exampleModifyDTO);
        return Rest.ok(modifiedExampleDO);
    }

    @PostMapping("/create")
    public Rest<ExampleDO> create(@RequestBody ExampleCreateDTO exampleCreateDTO) {
        ExampleDO modifiedExampleDO = exampleService.create(exampleCreateDTO);
        return Rest.ok(modifiedExampleDO);
    }

    @GetMapping("/delete/{id}")
    public Rest<Boolean> delete(@PathVariable("id") Long id) {
        exampleService.delete(id);
        return Rest.ok(true);
    }

}
