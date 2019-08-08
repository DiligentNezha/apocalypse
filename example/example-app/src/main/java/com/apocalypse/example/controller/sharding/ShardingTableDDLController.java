package com.apocalypse.example.controller.sharding;

import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api(value = "分表DDL", tags = {"分表"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShardingTableDDLController {

    @Autowired
    private DataSource dataSource;

    public static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS `sharding_table`;";

    public static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE `sharding_table`;";

    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE `sharding_table`  (\n" +
            "  `id` bigint(20) NOT NULL COMMENT '编号',\n" +
            "  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',\n" +
            "  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除：1：已删除；0：未删除',\n" +
            "  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
            "  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分表' ROW_FORMAT = Dynamic;";

    /**
     * 删除表
     */
    @GetMapping("/table/drop")
    @ApiOperation(value = "删除表", notes = "执行删除逻辑表的DDL语句，相关物理表会进行删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Boolean> dropTable() throws SQLException {
        dataSource.getConnection().createStatement().execute(DROP_TABLE_SQL);
        return Rest.ok(true);
    }

    /**
     * 清空表
     */
    @GetMapping("/table/truncate")
    @ApiOperation(value = "清空表", notes = "执行清空逻辑表的DDL语句，相关物理表会进行清空", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Boolean> truncateTable() throws SQLException {
        dataSource.getConnection().createStatement().execute(TRUNCATE_TABLE_SQL);
        return Rest.ok(true);
    }

    /**
     * 创建表
     */
    @GetMapping("/table/create")
    @ApiOperation(value = "创建表", notes = "执行创建逻辑表的DDL语句，相关物理表会进行创建", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<Boolean> createTable() throws SQLException {
        dataSource.getConnection().createStatement().execute(CREATE_TABLE_SQL);
        return Rest.ok(true);
    }
}
