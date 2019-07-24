package com.apocalypse.example.controller.sharding;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.util.PageConvertUtil;
import com.apocalypse.example.model.ShardingYearMonthDO;
import com.apocalypse.example.service.single.ShardingYearMonthService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.weekend.Weekend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/sharding/dml")
@Api(value = "按年和月分库分表DML", tags = {"按年和月分库分表"}, consumes = "application/json")
public class ShardingYearMonthDMLController {

    @Autowired
    private ShardingYearMonthService shardingYearMonthService;

    @GetMapping("/yearmonth/insert")
    @ApiOperation(value = "插入数据", notes = "根据分库分表规则，数据会分散的存入对应的数据源的相关物理表", produces = "application/json")
    public Rest<Boolean> insert() {
        for (int i = 1; i <= 1000; i++) {
            Long id = SnowflakeIdGenId.nextId() + RandomUtil.randomInt(2);
            int month = RandomUtil.randomInt(12) + 1;
            int day = RandomUtil.randomInt(27) + 1;
            LocalDateTime orderDate = LocalDateTime.of(2019, month, day, 0, 0, 0);
            String remark = StrUtil.format("Id is {}, orderDate is {}，I should save in ds{}.sharding_table_{}", id,
                    orderDate, id % 2, orderDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            ShardingYearMonthDO shardingYearMonthDO = new ShardingYearMonthDO()
                    .setId(id)
                    .setOrderDate(orderDate)
                    .setRemark(remark);
            shardingYearMonthService.insertSelective(shardingYearMonthDO);
        }
        return Rest.ok(true);
    }

    @GetMapping("/yearmonth/query/id")
    @ApiOperation(value = "根据Id查询", notes = "Id作为分库键，下单时间作为分表键，根据Id查询时，只能得到具体的数据源，因此实际执行的SQL是数据源和该数据源下相关表的全排列组成的SQL", produces = "application/json")
    public Rest<ShardingYearMonthDO> queryById(@RequestParam("id") Long id) {
        ShardingYearMonthDO shardingYearMonthDO = shardingYearMonthService.selectByPrimaryKey(id);
        return Rest.ok(shardingYearMonthDO);
    }

    @GetMapping("/yearmonth/query/orderDate")
    @ApiOperation(value = "根据orderDate查询", notes = "", produces = "application/json")
    public Rest<List<ShardingYearMonthDO>> queryByOrderDate(@RequestParam("orderDate") LocalDateTime orderDate) {
        List<ShardingYearMonthDO> shardingYearMonthDOS =
                shardingYearMonthService.selectByProperty(ShardingYearMonthDO::getOrderDate, orderDate);
        return Rest.ok(shardingYearMonthDOS);
    }

    @GetMapping("/yearmonth/query/in")
    @ApiOperation(value = "in查询", notes = "", produces = "application/json")
    public Rest<List<ShardingYearMonthDO>> queryIn(@RequestParam("ids") List<Long> ids,
                                                   @RequestParam("pageNum") int pageNum,
                                                   @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShardingYearMonthDO> shardingYearMonthDOS = shardingYearMonthService.selectByIdList(ids);
        return Rest.ok(shardingYearMonthDOS).setPage(PageConvertUtil.convert(shardingYearMonthDOS));
    }

    @GetMapping("/yearmonth/query/between")
    @ApiOperation(value = "between查询", notes = "", produces = "application/json")
    public Rest<List<ShardingYearMonthDO>> queryBetween(@RequestParam("orderDateBegin") LocalDateTime orderDateBegin,
                                                        @RequestParam("orderDateEnd") LocalDateTime orderDateEnd) {
        // Inline strategy cannot support range sharding
        Weekend<ShardingYearMonthDO> weekend = Weekend.of(ShardingYearMonthDO.class);
        weekend.weekendCriteria()
                .andBetween(ShardingYearMonthDO::getOrderDate, orderDateBegin, orderDateEnd);
        List<ShardingYearMonthDO> shardingYearMonthDOS = shardingYearMonthService.selectByExample(weekend);
        return Rest.ok(shardingYearMonthDOS);
    }
}
