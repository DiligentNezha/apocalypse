package com.apocalypse.example.model;

import javax.persistence.*;
import java.io.Serializable;

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
    private Byte age;

    /**
     * 性别【0：女性；1：男性；2：保密】
     */
    private Byte sex;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取用户真实姓名
     *
     * @return real_name - 用户真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置用户真实姓名
     *
     * @param realName 用户真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户年龄
     *
     * @return age - 用户年龄
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 设置用户年龄
     *
     * @param age 用户年龄
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 获取性别【0：女性；1：男性；2：保密】
     *
     * @return sex - 性别【0：女性；1：男性；2：保密】
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别【0：女性；1：男性；2：保密】
     *
     * @param sex 性别【0：女性；1：男性；2：保密】
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }
}