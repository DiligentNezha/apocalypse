package com.gkjx.saas.health.system.model.single;

import com.gkjx.common.core.module.TreeNode;
import com.gkjx.common.data.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Table(name = "organ")
public class Organ implements TreeNode<Organ>, Serializable {
    /**
     * 主键 ID
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 上级机构ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     *
     * 机构代码
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构类型：1：卫健委；5：医院；10：社区卫生中心；15：社区卫生服务站；20：科室
     */
    @Column(name = "organ_type")
    private Integer organType;

    /**
     * 排序值
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 是否启用：1：已启用；0：未启用
     */
    private Boolean enabled;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 联系人
     */
    @Column
    private String contact;

    /**
     * 联系人电话
     */
    @Column(name = "contact_mobile")
    private String contactMobile;

    /**
     * 邮箱
     */
    private String email;

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

    @Transient
    private List<Organ> children;

    @Override
    public void setChildren(List<Organ> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organ organ = (Organ) o;
        return Objects.equals(id, organ.id);
    }

}