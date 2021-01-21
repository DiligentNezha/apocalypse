package com.apocalypse.idaas.module.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.data.mybatis.type.JSONArrayTypeHandler;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Data
@Accessors(chain = true)
@Table(name = "oauth_approvals")
public class OAuthApprovals implements Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 用户 ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 客户端 ID
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 状态
     */
    private String status;

    /**
     * 失效时间
     */
    @Column(name = "expires_at")
    private Date expiresAt;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    /**
     * 授权范围
     */
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode scope;

    /**
     * 是否删除：1：已删除；0：未删除
     */
    @Column(name = "is_deleted")
    private Boolean deleted;

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