package com.apocalypse.example.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.single.ShardingTableDOMapper;
import com.apocalypse.example.model.ShardingTableDO;
import com.apocalypse.example.service.single.ShardingTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/22
 */
@Slf4j
@Service
public class ShardingTableServiceImpl extends BaseServiceImpl<ShardingTableDO, Long> implements ShardingTableService {

    @Autowired
    private ShardingTableDOMapper shardingTableDOMapper;

}
