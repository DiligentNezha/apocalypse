package com.apocalypse.example.controller.sharding;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import com.apocalypse.example.model.ShardingTableDO;
import com.apocalypse.example.service.single.ShardingTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
@Api(value = "分表DML", tags = {"分表"}, consumes = "application/json")
public class ShardingTableDMLController {

    @Autowired
    private ShardingTableService shardingTableService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/shardingtable/insert")
    @ApiOperation(value = "插入数据", notes = "根据分片规则，数据会分散的存入相关物理表", produces = "application/json")
    public Rest<Boolean> insert() {
        for (int i = 1; i <= 1000; i++) {
            Long id = SnowflakeIdGenId.nextId();
            String remark = StrUtil.format("Id is {}, I should save in ds0.sharding_table_{}", id, id % 5);
            ShardingTableDO shardingTableDO = new ShardingTableDO()
                    .setId(id)
                    .setRemark(remark);
            shardingTableService.insertDB(shardingTableDO);
        }
        return Rest.ok(true);
    }

    @GetMapping("/shardingtable/query/{id}")
    @ApiOperation(value = "根据Id查询", notes = "", produces = "application/json")
    public Rest<ShardingTableDO> query(@PathVariable("id") Long id) {
        ShardingTableDO shardingTableDO = shardingTableService.queryById(id);
        return Rest.ok(shardingTableDO);
    }

    @GetMapping("/shardingtable/query/in")
    @ApiOperation(value = "in查询", notes = "", produces = "application/json")
    public Rest<List<ShardingTableDO>> queryIn(@RequestParam("ids") List<Long> ids) {
        List<ShardingTableDO> shardingTableDOS = shardingTableService.queryIn(ids);
        return Rest.ok(shardingTableDOS);
    }

    @GetMapping("/shardingtable/query/between")
    @ApiOperation(value = "between查询", notes = "", produces = "application/json")
    public Rest<List<ShardingTableDO>> queryBetween(@RequestParam("begin") Long begin,
                                                    @RequestParam("end") Long end) {
        // Inline strategy cannot support range sharding
        List<ShardingTableDO> shardingTableDOS = shardingTableService.queryBetween(begin, end);
        return Rest.ok(shardingTableDOS);
    }
}
