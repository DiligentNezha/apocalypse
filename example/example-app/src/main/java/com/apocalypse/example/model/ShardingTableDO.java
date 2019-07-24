package com.apocalypse.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "sharding_table")
public class ShardingTableDO implements Serializable {
    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除：1：已删除；0：未删除
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
