package com.apocalypse.example.model;

import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import java.time.LocalDateTime;
import javax.persistence.*;
@Data
@ApiModel
@Accessors(chain = true)
@Table(name = "example_extend")
public class ExampleExtendDO {
    /**
     * 编号
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 别名
     */
    private String alias;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除（1：已删除；0：未删除）
     */
    @LogicDelete(notDeletedValue = 0, isDeletedValue = 1)
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}