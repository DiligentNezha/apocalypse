package com.apocalypse.cms.model.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 双色排列池(NumberArrange)实体类
 *
 * @author makejava
 * @since 2021-03-07 12:07:10
 */
@Data
@Accessors(chain = true)
@Table(name = "number_arrange")
public class NumberArrange implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 红 1
     */
    @Column(name = "red_one")
    private Object redOne;

    /**
     * 红 2
     */
    @Column(name = "red_two")
    private Object redTwo;

    /**
     * 红 3
     */
    @Column(name = "red_three")
    private Object redThree;

    /**
     * 红 4
     */
    @Column(name = "red_four")
    private Object redFour;

    /**
     * 红 5
     */
    @Column(name = "red_five")
    private Object redFive;

    /**
     * 红 6
     */
    @Column(name = "red_six")
    private Object redSix;

    /**
     * 蓝
     */
    @Column(name = "blue")
    private Object blue;

    /**
     * 组合排列格式串
     */
    @Column(name = "format")
    private String format;

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
     * 创建代理身份 ID
     */
    @Column(name = "create_agent_identity_id")
    private Long createAgentIdentityId;

    /**
     * 更新代理身份 ID
     */
    @Column(name = "update_agent_identity_id")
    private Long updateAgentIdentityId;

    /**
     * 创建账户 ID
     */
    @Column(name = "create_account_id")
    private Long createAccountId;

    /**
     * 更新账户 ID
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
