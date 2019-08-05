package com.apocalypse.example.controller;

import cn.hutool.core.util.NumberUtil;
import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/lambda")
@Api(value = "Lambda", tags = {"Lambda"}, consumes = "application/json")
public class LambdaController {

    @GetMapping("/predicate")
    @ApiOperation(value = "predicate", notes = "Predicate测试")
    public Rest<List<Integer>> hello() {
        List<Integer> collect = IntStream.rangeClosed(2, 1000)
                .filter(((IntPredicate) NumberUtil::isPrimes).and(value -> String.valueOf(value).length() > 2))
                .boxed().collect(Collectors.toList());
        return Rest.ok(collect);
    }

}
