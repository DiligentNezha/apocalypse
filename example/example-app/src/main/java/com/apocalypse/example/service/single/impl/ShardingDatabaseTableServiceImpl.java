package com.apocalypse.example.service.single.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.single.ShardingDatabaseTableDOMapper;
import com.apocalypse.example.model.ShardingDatabaseTableDO;
import com.apocalypse.example.service.single.ShardingDatabaseTableService;
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
public class ShardingDatabaseTableServiceImpl extends BaseServiceImpl<ShardingDatabaseTableDO, Long> implements ShardingDatabaseTableService {

    @Autowired
    private ShardingDatabaseTableDOMapper shardingDatabaseTableDOMapper;
}
