package com.gkjx.saas.health.system.model.single;

import com.gkjx.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
@Data
@Accessors(chain = true)
@Table(name = "role_element_union")
public class RoleElementUnion implements Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 元素ID
     */
    @Column(name = "element_id")
    private Long elementId;

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
        RoleElementUnion that = (RoleElementUnion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}