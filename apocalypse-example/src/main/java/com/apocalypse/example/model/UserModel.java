package com.apocalypse.example.model;

import com.apocalypse.common.model.GeneratedKeysModel;

import javax.persistence.*;

@Table(name = "t_example_user")
public class UserModel extends GeneratedKeysModel {

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户年龄
     */
    private Byte age;

    /**
     * 性别【1：男性；0：女性；2：保密】
     */
    private Byte sex;

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
     * 获取性别【1：男性；0：女性；-1：保密】
     *
     * @return sex - 性别【1：男性；0：女性；-1：保密】
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别【1：男性；0：女性；-1：保密】
     *
     * @param sex 性别【1：男性；0：女性；-1：保密】
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

}