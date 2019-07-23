package com.apocalypse.example.controller.sharding;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import com.apocalypse.example.model.ShardingDatabaseTableDO;
import com.apocalypse.example.service.single.ShardingDatabaseTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/sharding/dml")
@Api(value = "分库分表DML", tags = {"分库分表"}, consumes = "application/json")
public class ShardingDatabaseTableDMLController {

    @Autowired
    private ShardingDatabaseTableService shardingDatabaseTableService;

    @GetMapping("/databasetable/insert")
    @ApiOperation(value = "插入数据", notes = "根据分库分表规则，数据会分散的存入对应的数据源的相关物理表", produces = "application/json")
    public Rest<Boolean> insert() {
        for (int i = 1; i <= 1000; i++) {
            Long id = SnowflakeIdGenId.nextId() + RandomUtil.randomInt(2);
            String remark = StrUtil.format("Id is {}, I should save in ds{}.sharding_table_{}", id, id % 2, id % 5);
            ShardingDatabaseTableDO shardingDatabaseTableDO = new ShardingDatabaseTableDO()
                    .setId(id)
                    .setRemark(remark);
            shardingDatabaseTableService.insertSelective(shardingDatabaseTableDO);
        }
        return Rest.ok(true);
    }

    @GetMapping("/databasetable/query/{id}")
    @ApiOperation(value = "根据Id查询", notes = "", produces = "application/json")
    public Rest<ShardingDatabaseTableDO> query(@PathVariable("id") Long id) {
        ShardingDatabaseTableDO shardingDatabaseTableDO = shardingDatabaseTableService.selectByPrimaryKey(id);
        return Rest.ok(shardingDatabaseTableDO);
    }

    @GetMapping("/databasetable/query/in")
    @ApiOperation(value = "in查询", notes = "", produces = "application/json")
    public Rest<List<ShardingDatabaseTableDO>> queryIn(@RequestParam("ids") List<Long> ids) {
        List<ShardingDatabaseTableDO> shardingDatabaseTableDOS = shardingDatabaseTableService.selectByIdList(ids);
        return Rest.ok(shardingDatabaseTableDOS);
    }

    @GetMapping("/databasetable/query/between")
    @ApiOperation(value = "between查询", notes = "", produces = "application/json")
    public Rest<List<ShardingDatabaseTableDO>> queryBetween(@RequestParam("begin") Long begin,
                                                    @RequestParam("end") Long end) {
        // Inline strategy cannot support range sharding
        Weekend<ShardingDatabaseTableDO> weekend = Weekend.of(ShardingDatabaseTableDO.class);
        weekend.weekendCriteria()
                .andBetween(ShardingDatabaseTableDO::getId, begin, end);
        List<ShardingDatabaseTableDO> shardingTableDOS = shardingDatabaseTableService.selectByExample(weekend);
        return Rest.ok(shardingTableDOS);
    }
}
