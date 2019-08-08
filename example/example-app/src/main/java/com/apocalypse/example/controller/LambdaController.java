package com.apocalypse.example.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
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
@Api(value = "Lambda", tags = {"Lambda"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LambdaController {

    @GetMapping("/predicate")
    @ApiOperation(value = "Predicate", notes = "Predicate测试", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rest<List<Integer>> predicate() {
        List<Integer> collect = IntStream.rangeClosed(2, 1000)
                //素数并且第一位和第三位值一样大
                .filter(((IntPredicate) NumberUtil::isPrimes).and(value -> value / 100 == value % 10))
                .boxed().collect(Collectors.toList());
        return Rest.ok(collect);
    }

    @GetMapping("/function")
    @ApiOperation(value = "Function", notes = "Function测试", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rest<String> function() {
        Function<String, Integer> toInteger = Integer::valueOf;
        //把String转成数字，再把数字转回去
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        return Rest.ok(backToString.apply("123"));
    }

    @GetMapping("/supply")
    @ApiOperation(value = "Supply", notes = "Supply测试", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rest<JSONObject> supply() {
        JSONObject result = new JSONObject();
        Supplier<Address> addressSupplier = Faker.instance(Locale.getDefault())::address;
        Address address = addressSupplier.get();
        Class<? extends Address> addressClass = address.getClass();
        Method[] declaredMethods = addressClass.getDeclaredMethods();
        Arrays.stream(declaredMethods)
                .filter(method -> method.getParameterCount() == 0)
                .forEach(method -> {
                    try {
                        result.put(method.getName(), method.invoke(address));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
        return Rest.ok(result);
    }

    @GetMapping("/comparator")
    @ApiOperation(value = "Comparator", notes = "Comparator测试", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Rest<List<Integer>> comparator() {
        List<Integer> collect = IntStream.rangeClosed(1, 10)
                .map(operand -> operand + RandomUtil.randomInt(10))
                .sorted()
                .skip(5)
                .limit(3)
                .boxed()
                .collect(Collectors.toList());
        return Rest.ok(collect);
    }
}
