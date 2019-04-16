package com.apocalypse.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "t_example_user")
public class UserModel implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    private String mail;

    private String password;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 性别【0：女性；1：男性；2：保密】
     */
    private Byte sex;

}