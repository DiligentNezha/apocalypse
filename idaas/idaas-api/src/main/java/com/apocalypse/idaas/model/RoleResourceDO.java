package com.apocalypse.idaas.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
@Data
@ApiModel
@Accessors(chain = true)
@Table(name = "role_resource_union")
public class RoleResourceDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 资源编号
     */
    @Column(name = "resource_id")
    private Integer resourceId;

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