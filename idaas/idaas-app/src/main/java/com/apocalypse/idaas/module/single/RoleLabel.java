package com.apocalypse.idaas.module.single;

import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Table(name = "role_label")
public class RoleLabel implements Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 机构 ID
     */
    @Column(name = "organ_id")
    private Long organId;

    /**
     * 角色标签名称
     */
    private String name;

    /**
     * 角色所属平台：1：医院管理端；5：医院 PC
     */
    private Integer platform;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除：1：已删除；0：未删除
     */
    @LogicDelete
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 创建人 ID
     */
    @Column(name = "create_identity_id")
    private Long createIdentityId;

    /**
     * 更新人 ID
     */
    @Column(name = "update_identity_id")
    private Long updateIdentityId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleLabel roleLabel = (RoleLabel) o;
        return Objects.equals(id, roleLabel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}