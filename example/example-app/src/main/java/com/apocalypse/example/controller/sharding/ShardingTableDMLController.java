package com.apocalypse.example.controller.sharding;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import com.apocalypse.example.model.ShardingTableDO;
import com.apocalypse.example.service.single.ShardingTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/sharding/dml")
@Api(value = "分表DML", tags = {"分表"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShardingTableDMLController {

    @Autowired
    private ShardingTableService shardingTableService;

    @GetMapping("/table/insert")
    @ApiOperation(value = "插入数据", notes = "根据分片规则，数据会分散的存入相关物理表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> insert() {
        for (int i = 1; i <= 1000; i++) {
            Long id = SnowflakeIdGenId.nextId() + RandomUtil.randomInt(2);
            String remark = StrUtil.format("Id is {}, I should save in ds0.sharding_table_{}", id, id % 5);
            ShardingTableDO shardingTableDO = new ShardingTableDO()
                    .setId(id)
                    .setRemark(remark);
            shardingTableService.insertSelective(shardingTableDO);
        }
        return Rest.success();
    }

    @GetMapping("/table/query/{id}")
    @ApiOperation(value = "根据Id查询", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> query(@PathVariable("id") Long id) {
        ShardingTableDO shardingTableDO = shardingTableService.selectByPrimaryKey(id);
        return Rest.vector("content", shardingTableDO, ShardingTableDO.class);
    }

    @GetMapping("/table/query/in")
    @ApiOperation(value = "in查询", notes = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> queryIn(@RequestParam("ids") List<Long> ids) {
        List<ShardingTableDO> shardingTableDOS = shardingTableService.selectByIdList(ids);
        return Rest.vector("content", shardingTableDOS, List.class);
    }

}
