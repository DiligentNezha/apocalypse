package com.apocalypse.cms.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.cms.model.single.NumberArrange;
import com.apocalypse.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RList;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.apocalypse.cms.service.single.NumberArrangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 双色排列池(NumberArrange)表控制层
 *
 * @author makejava
 * @since 2021-03-07 12:07:06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ball/arrange")
@Api(value = "NumberArrange", tags = {"NumberArrange"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class NumberArrangeController {

    @Autowired
    private NumberArrangeService numberArrangeService;

    @Autowired
    private RedissonClient redissonClient;

    public static void main(String[] args) {
        System.out.println(new NumberArrangeController().add(null));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "添加", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> add(@Validated @RequestBody NumberArrange request) {
        RQueue<Object> queue = redissonClient.getQueue("double:happy");
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int blue = 1; blue <= 16; blue++) {
            for (int one = 1; one <= 28; one++) {
                for (int two = one + 1; two <= 29; two++) {
                    for (int three = two + 1; three <= 30; three++) {
                        for (int four = three + 1; four <= 31; four++) {
                            for (int five = four + 1; five <= 32; five++) {
                                for (int six = five + 1; six <= 33; six++) {
                                    LocalDateTime now = LocalDateTime.now();
                                    snowflake.nextId();
                                    NumberArrange numberArrange = new NumberArrange()
                                            .setId(snowflake.nextId())
                                            .setRedOne(one)
                                            .setRedTwo(two)
                                            .setRedThree(three)
                                            .setRedFour(four)
                                            .setRedFive(five)
                                            .setRedSix(six)
                                            .setBlue(blue)
                                            .setIsDeleted(false)
                                            .setCreateIdentityId(0L)
                                            .setUpdateIdentityId(0L)
                                            .setCreateAccountId(0L)
                                            .setUpdateAccountId(0L)
                                            .setCreateAgentIdentityId(0L)
                                            .setUpdateAgentIdentityId(0L)
                                            .setCreateTime(now)
                                            .setUpdateTime(now);
                                    StringBuilder format = new StringBuilder();
                                    format.append(String.format("%2d ", one));
                                    format.append(String.format("%2d ", two));
                                    format.append(String.format("%2d ", three));
                                    format.append(String.format("%2d ", four));
                                    format.append(String.format("%2d ", five));
                                    format.append(String.format("%2d+", six));
                                    format.append(String.format("%2d", blue));
                                    numberArrange.setFormat(format.toString());
                                    System.out.println("double:happy:" + format);
                                    queue.add(numberArrange);
                                }
                            }
                        }
                    }
                }
            }
        }

        return Rest.success();
    }

    @PostMapping("/consumer")
    @ApiOperation(value = "消费", notes = "消费", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> consumer() {
        RQueue<Object> queue = redissonClient.getQueue("double:happy");
        int count = 10;
        do {
            List<Object> poll = queue.poll(3000);
            if (poll.size() > 0) {
                List<NumberArrange> collect = poll.stream().map(e -> (NumberArrange) e).collect(Collectors.toList());
                numberArrangeService.insertList(collect);
            } else {
                count--;
            }
        } while (count > 0);
        return Rest.success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> update(@Validated @RequestBody NumberArrange request) {
        Long id = request.getId();
        NumberArrange numberArrange = numberArrangeService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(numberArrange)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        numberArrangeService.updateByPrimaryKeySelective(request);
        return Rest.vector("id", request.getId(), Long.class);
    }

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "详情", notes = "详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> detail(@PathVariable Long id) {
        NumberArrange numberArrange = numberArrangeService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(numberArrange)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        return Rest.vector("detail", numberArrange, NumberArrange.class);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> delete(@PathVariable Long id) {
        numberArrangeService.deleteByPrimaryKey(id);
        return Rest.vector("deleted", true, Boolean.class);
    }

}
