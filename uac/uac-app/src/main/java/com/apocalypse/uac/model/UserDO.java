package com.apocalypse.uac.model;

import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Accessors(chain = true)
@Table(name = "user")
public class UserDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 账号是否锁定（1：锁定；0：未锁定）
     */
    private Integer accountLocked;

    /**
     * 账号是否可用（1：可用；0：不可用）
     */
    private Integer enabled;

    @Transient
    private Set<GrantedAuthority> authorities;

    /**
     * 是否删除（1：已删除；0：未删除）
     */
    @LogicDelete
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
