package com.apocalypse.example.controller.sharding;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.example.mapper.single.OrderDOMapper;
import com.apocalypse.example.model.OrderDO;
import com.apocalypse.example.service.single.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/sharding/dml")
public class ShardingDMLController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/order/insert")
    public Rest<Boolean> insert() throws SQLException {
        for (int i = 1; i <= 1; i++) {
            OrderDO order = new OrderDO()
                    .setOrderId(i)
                    .setUserId(RandomUtil.randomInt(1, 100000))
                    .setStatus("created");
            orderService.insertSelective(order);
        }
        return Rest.ok(true);
    }
}
