package com.apocalypse.idaas.module.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

@Data
@Accessors(chain = true)
@Table(name = "oauth_access_token")
public class OAuthAccessToken implements Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * token id
     */
    @Column(name = "token_id")
    private String tokenId;

    /**
     * token
     */
    private String token;

    /**
     * 授权身份 ID
     */
    @Column(name = "authentication_id")
    private String authenticationId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 客户端 ID
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 授权身份
     */
    private String authentication;

    /**
     * refresh token
     */
    @Column(name = "refresh_token")
    private String refreshToken;

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