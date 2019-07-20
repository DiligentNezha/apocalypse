package com.apocalypse.example.controller.sharding;

import com.apocalypse.common.dto.Rest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/20
 */
@Slf4j
@RestController
@RequestMapping("/sharding/ddl")
public class ShardingDDLController {

    @Autowired
    private DataSource dataSource;

    public static final String CREATE_TABLE_SQL = "DROP TABLE IF EXISTS `t_order_0`;\n" +
            "CREATE TABLE `t_order_0`  (\n" +
            "  `order_id` int(11) NOT NULL,\n" +
            "  `user_id` int(11) NOT NULL,\n" +
            "  `status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`order_id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;";


    @GetMapping("/table/create")
    public Rest<Boolean> createTable() throws SQLException {
        // 配置Order表规则
        TableRuleConfiguration tableRuleOrder = new TableRuleConfiguration("t_order", "ds${0..1}.t_order_${0..1}");
        // 配置分库 + 分表策略
        tableRuleOrder.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        tableRuleOrder.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

        TableRuleConfiguration tableRuleOrderItem = new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item_${0..1}");
        // 配置分库 + 分表策略
        tableRuleOrderItem.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_item_$->{order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRule = new ShardingRuleConfiguration();
        shardingRule.getTableRuleConfigs().add(tableRuleOrder);
        shardingRule.getTableRuleConfigs().add(tableRuleOrderItem);

        //TODO

        dataSource.getConnection().createStatement().execute(CREATE_TABLE_SQL);
        return Rest.ok(true);
    }
}
