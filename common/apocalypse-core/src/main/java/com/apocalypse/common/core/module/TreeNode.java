package com.apocalypse.common.core.module;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/27
 */
public interface TreeNode<T> {

    /**
     * 获取当前树及诶点 ID
     * @return
     */
    Long getId();

    /**
     * 获取当前节点名称
     * @return
     */
    String getName();

    /**
     * 获取父级 ID
     * @return
     */
    Long getParentId();

    /**
     * 获取子节点
     * @return
     */
    List<T> getChildren();

    /**
     * 获取节点排序值
     * @return
     */
    Integer getOrderNum();

    /**
     * 设置子节点列表
     */
    void setChildren(List<T> children);

    /**
     * 是否是顶级树节点
     * @return
     */
    @JsonIgnore
    default boolean isTopNode() {
        return getParentId() == 0;
    }
}
