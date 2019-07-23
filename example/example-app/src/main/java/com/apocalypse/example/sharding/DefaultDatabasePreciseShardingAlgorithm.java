package com.apocalypse.example.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/22
 */
public class DefaultDatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        String[] targetNames = new String[availableTargetNames.size()];
        availableTargetNames.toArray(targetNames);
        return targetNames[(int) (shardingValue.getValue() % availableTargetNames.size())];
    }
}
