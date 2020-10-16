package com.gkjx.saas.health.system.model.single;

import com.gkjx.common.data.mybatis.SnowflakeIdGenId;
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
@Table(name = "staff")
public class Staff implements Serializable {
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
     * 员工编号
     */
    @Column(name = "staff_identity")
    private String staffIdentity;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：1：男；2：女；3：保密
     */
    private Integer gender;

    /**
     * 身份证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 紧急联系人
     */
    @Column(name = "urgency_contact")
    private String urgencyContact;

    /**
     * 紧急联系人电话
     */
    @Column(name = "urgency_contact_mobile")
    private String urgencyContactMobile;

    /**
     * 是否已离职：1：已离职；0：未离职
     */
    @Column(name = "is_leave_office")
    private Boolean isLeaveOffice;

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
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}