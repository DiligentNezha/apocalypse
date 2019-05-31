package com.apocalypse.example.model;

import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import java.time.LocalDateTime;
import javax.persistence.*;
@Data
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
     * 是否删除
     */
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