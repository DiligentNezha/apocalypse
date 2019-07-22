package com.apocalypse.example.service.single.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
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

    @Override
    public void insertDB(ShardingTableDO shardingTableDO) {
        shardingTableDOMapper.insertDB(shardingTableDO);
    }

    @Override
    public ShardingTableDO queryById(Long id) {
        return shardingTableDOMapper.queryById(id);
    }

    @Override
    public List<ShardingTableDO> queryBetween(Long begin, Long end) {
        return shardingTableDOMapper.queryBetween(begin, end);
    }

    @Override
    public List<ShardingTableDO> queryIn(List<Long> ids) {
        return shardingTableDOMapper.queryIn(ids);
    }
}
