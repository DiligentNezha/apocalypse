package com.apocalypse.idaas.module.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.data.mybatis.type.JSONArrayTypeHandler;
import com.apocalypse.common.data.mybatis.type.JSONObjectTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

@Data
@Accessors(chain = true)
@Table(name = "oauth_client_details")
public class OAuthClientDetails implements Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 客户端名
     */
    @Column(name = "client_name")
    private String clientName;

    /**
     * 客户端描述
     */
    private String description;

    /**
     * 客户端 ID
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 客户端秘钥
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 服务跳转路径
     */
    @Column(name = "web_server_redirect_uri")
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode webServerRedirectUri;

    /**
     * access_token 有效期，默认为一天
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * refresh_token 有效期，默认为一个月
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 资源 ID 列表
     */
    @Column(name = "resource_ids")
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode resourceIds;

    /**
     * 授权范围
     */
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode scope;

    /**
     * 授权模式
     */
    @Column(name = "authorized_grant_types")
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode authorizedGrantTypes;

    /**
     * 权限集合
     */
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode authorities;

    /**
     * 附加信息
     */
    @Column(name = "additional_information")
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private ObjectNode additionalInformation;

    /**
     * 自动批准
     */
    @Column(name = "auto_approve")
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private ArrayNode autoApprove;

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