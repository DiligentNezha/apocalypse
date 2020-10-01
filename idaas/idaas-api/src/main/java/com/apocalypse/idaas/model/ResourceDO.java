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
@Table(name = "resource")
public class ResourceDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父级资源编号
     */
    private Integer pid;

    /**
     * 系统编号
     */
    @Column(name = "system_id")
    private Integer systemId;

    /**
     * 展示名
     */
    @Column(name = "display_name")
    private String displayName;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 资源类型（1：菜单；2：组件；3：按钮）
     */
    private Integer type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限标识
     */
    private String sign;

    /**
     * 资源层级
     */
    private Integer level;

    /**
     * 顺序
     */
    private Integer orders;

    /**
     * 需要授权
     */
    @Column(name = "require_auth")
    private Integer requireAuth;

    /**
     * 是否可见
     */
    private Integer visible;

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