package com.apocalypse.example.service.single;

import com.apocalypse.common.service.BaseService;
import com.apocalypse.example.model.ShardingTableDO;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/22
 */
public interface ShardingTableService extends BaseService<ShardingTableDO, Long> {

    void insertDB(ShardingTableDO shardingTableDO);

    ShardingTableDO queryById(Long id);

    List<ShardingTableDO> queryBetween(Long begin, Long end);

    List<ShardingTableDO> queryIn(List<Long> ids);

}
