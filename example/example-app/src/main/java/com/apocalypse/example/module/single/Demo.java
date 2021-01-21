package com.apocalypse.example.module.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.data.mybatis.type.JSONObjectTypeHandler;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源表(Demo)实体类
 *
 * @author makejava
 * @since 2020-11-07 08:04:27
 */
@Data
@Accessors(chain = true)
@Table(name = "demo")
public class Demo implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * tinyint_test 测试
     */
    @Column(name = "tinyint_test")
    private Boolean tinyintTest;

    /**
     * 资源所属平台：1：医生管理端；5：医生端
     */
    @Column(name = "platform")
    private Integer platform;

    /**
     * 资源名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 资源标识
     */
    @Column(name = "sign")
    private String sign;

    /**
     * 路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 资源类型：1：application/json；5：application/octet-stream；
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 是否需要授权：1：需要；0：不需要
     */
    @Column(name = "require_auth")
    private Boolean requireAuth;

    /**
     * 属性
     */
    @Column(name = "props")
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private ObjectNode props;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否删除：1：已删除；0：未删除
     */
    @LogicDelete
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建身份 ID
     */
    @Column(name = "create_identity_id")
    private Long createIdentityId;

    /**
     * 更新身份 ID
     */
    @Column(name = "update_identity_id")
    private Long updateIdentityId;

    /**
     * 创建账户 ID
     */
    @Column(name = "create_account_id")
    private Long createAccountId;

    /**
     * 更新身份 ID
     */
    @Column(name = "update_account_id")
    private Long updateAccountId;

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