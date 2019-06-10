package com.apocalypse.example.controller;

import com.apocalypse.common.dto.Rest;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.service.single.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheExampleController {

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(cacheNames = "exampleCache")
    @GetMapping("/query/{id}")
    public Rest<ExampleDO> query(@PathVariable("id") Long id) {
        ExampleDO exampleDO = exampleService.selectByPrimaryKey(id);
        return Rest.ok(exampleDO);
    }

}
