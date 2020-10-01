package com.apocalypse.example.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.single.ShardingYearnMonthDOMapper;
import com.apocalypse.example.model.ShardingYearMonthDO;
import com.apocalypse.example.service.single.ShardingYearMonthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/22
 */
@Slf4j
@Service
public class ShardingMonthServiceImpl extends BaseServiceImpl<ShardingYearMonthDO, Long> implements ShardingYearMonthService {

    @Autowired
    private ShardingYearnMonthDOMapper shardingYearnMonthDOMapper;

}
