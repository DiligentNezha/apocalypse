package com.apocalypse.example.service.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.single.DemoMapper;
import com.apocalypse.example.module.single.Demo;
import com.apocalypse.example.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 资源表(Demo)表服务实现类
 *
 * @author makejava
 * @since 2020-11-07 08:04:18
 */
@Slf4j
@Service
public class DemoServiceImpl extends BaseServiceImpl<Demo, Long> implements DemoService {
    @Resource
    private DemoMapper demoMapper;
}