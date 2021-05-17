package com.apocalypse.cms.service.single.impl;

import com.apocalypse.cms.model.single.NumberArrange;
import com.apocalypse.cms.mapper.single.NumberArrangeMapper;
import com.apocalypse.cms.service.single.NumberArrangeService;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 双色排列池(NumberArrange)表服务实现类
 *
 * @author makejava
 * @since 2021-03-07 12:07:04
 */
@Slf4j
@Service
public class NumberNumberArrangeServiceImpl extends BaseServiceImpl<NumberArrange, Long> implements NumberArrangeService {

    @Resource
    private NumberArrangeMapper numberArrangeMapper;
}
