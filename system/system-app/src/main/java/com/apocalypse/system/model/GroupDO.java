package com.apocalypse.system.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
@Data
@ApiModel
@Accessors(chain = true)
@Table(name = "group")
public class GroupDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 管理员组名
     */
    private String name;

    /**
     * 组类型（1：管理员组；2：角色组）
     */
    private Byte type;

    /**
     * 是否删除（1：已删除；0：未删除）
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}