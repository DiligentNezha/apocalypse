package com.apocalypse.cms.model.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 双色排列池(AwardedHistory)实体类
 *
 * @author makejava
 * @since 2021-03-07 12:07:14
 */
@Data
@Accessors(chain = true)
@Table(name = "awarded_history")
public class AwardedHistory implements Serializable {

    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 期号
     */
    @Column(name = "issue")
    private Integer issue;

    /**
     * 一周的哪天
     */
    @Column(name = "day_of_week")
    private String dayOfWeek;

    /**
     * 开奖日期
     */
    @Column(name = "reveal_date")
    private LocalDate revealDate;

    /**
     * 红 1
     */
    @Column(name = "red_one")
    private Integer redOne;

    /**
     * 红 2
     */
    @Column(name = "red_two")
    private Integer redTwo;

    /**
     * 红 3
     */
    @Column(name = "red_three")
    private Object redThree;

    /**
     * 红 4
     */
    @Column(name = "red_four")
    private Integer redFour;

    /**
     * 红 5
     */
    @Column(name = "red_five")
    private Integer redFive;

    /**
     * 红 6
     */
    @Column(name = "red_six")
    private Integer redSix;

    /**
     * 蓝
     */
    @Column(name = "blue")
    private Integer blue;

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
