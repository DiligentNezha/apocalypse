-- used in tests that use HSQL

CREATE TABLE `oauth_client_details` (
                           id bigint(20) unsigned not null comment '主键 ID',
                           client_id varchar(256) not null comment '客户端 ID',
                           resource_ids json not null comment '资源 ID 列表',
                           client_secret varchar(256) not null comment '客户端秘钥',
                           scope json not null comment '授权范围',
                           authorized_grant_types json not null comment '授权模式',
                           web_server_redirect_uri varchar(256) not null comment '服务跳转路径',
                           authorities json not null comment '权限集合',
                           access_token_validity int not null default 86400 comment 'access_token 有效期，默认为一天',
                           refresh_token_validity int not null default 2592000 comment 'refresh_token 有效期，默认为一个月',
                           additional_information json not null comment '附加信息',
                           auto_approve json not null comment '自动批准',
                           is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                           create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                           update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                           create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                           update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                           create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                           update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE KEY `uk_client_id_is_deleted` (`client_id`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2 客户端注册表';

CREATE TABLE `oauth_client_token` (
                                        id bigint(20) unsigned not null comment '主键 ID',
                                        token_id varchar(256) not null comment 'token id',
                                        token varchar(256) not null comment 'token',
                                        authentication_id varchar(256) not null comment '授权身份 ID',
                                        user_name varchar(256) not null comment '用户名',
                                        client_id varchar(256) not null comment '客户端 ID',
                                        is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                                        create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                                        update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                        create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                                        update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                        create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                                        update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `uk_client_id_is_deleted` (`client_id`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端 token 表';

CREATE TABLE `oauth_access_token` (
                                      id bigint(20) unsigned not null comment '主键 ID',
                                      token_id varchar(256) not null comment 'token id',
                                      token varchar(256) not null comment 'token',
                                      authentication_id varchar(256) not null comment '授权身份 ID',
                                      user_name varchar(256) not null comment '用户名',
                                      client_id varchar(256) not null comment '客户端 ID',
                                      authentication varchar(256) not null comment '授权身份',
                                      refresh_token varchar(256) not null comment 'refresh token',
                                      is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                                      create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                                      update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                      create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                                      update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                      create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                                      update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE KEY `uk_client_id_is_deleted` (`client_id`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端 Access Token 表';

CREATE TABLE `oauth_refresh_token` (
                                      id bigint(20) unsigned not null comment '主键 ID',
                                      token_id varchar(256) not null comment 'token id',
                                      token varchar(256) not null comment 'token',
                                      authentication varchar(256) not null comment '授权身份',
                                      is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                                      create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                                      update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                      create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                                      update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                      create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                                      update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      UNIQUE KEY `uk_token_id_is_deleted` (`token_id`,`is_deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端 Access Token 表';

CREATE TABLE `oauth_code` (
                                       id bigint(20) unsigned not null comment '主键 ID',
                                       code varchar(256) not null comment '授权码',
                                       authentication varchar(256) not null comment '授权身份',
                                       is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                                       create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                                       update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                       create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                                       update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                                       create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                                       update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端 Access Token 表';

CREATE TABLE `oauth_approvals` (
                              id bigint(20) unsigned not null comment '主键 ID',
                              user_id varchar(256) not null comment '用户 ID',
                              client_id varchar(256) not null comment '客户端 ID',
                              scope json not null comment '授权范围',
                              status varchar(10) not null comment '状态',
                              expires_at datetime not null comment '失效时间',
                              last_modified_at datetime not null comment '最后修改时间',
                              is_deleted tinyint(1) unsigned not null default '0' COMMENT '是否删除：1：已删除；0：未删除',
                              create_identity_id bigint(20) unsigned not null default '0' COMMENT '创建身份 ID',
                              update_identity_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                              create_account_id bigint(20) unsigned not null default '0' COMMENT '创建账户 ID',
                              update_account_id bigint(20) unsigned not null default '0' COMMENT '更新身份 ID',
                              create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2客户端 Access Token 表';

