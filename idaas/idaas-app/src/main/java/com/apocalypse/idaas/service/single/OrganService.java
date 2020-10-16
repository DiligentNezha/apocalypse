package com.apocalypse.idaas.service.single;

import com.apocalypse.common.data.mybatis.service.BaseService;
import com.apocalypse.idaas.module.single.Identity;
import com.apocalypse.idaas.module.single.Organ;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface OrganService extends BaseService<Organ, Long> {

    /**
     * 查找顶级组织机构 ID，即 parentId 为 0的节点
     * @return
     */
    Long findTopOrganId();

    /**
     * 添加机构
     * @param organ
     * @param identity
     * @return
     */
    Long addOrgan(Organ organ, Identity identity);

    /**
     * 构建以当前节点为根节点的子组织树
     */
    void buildOrganTree(Organ topOrgan);

    /**
     * 查找当前机构及其子机构
     * @param organId
     * @param organs 传入 null 即可
     * @return
     */
    List<Organ> findCurrentOrganAndChildren(Long organId, List<Organ> organs);

    /**
     * 判断两个组织机构是否是子父级管理（不一定是直接子父级）
     * @param parentOrgan
     * @param child
     * @return
     */
    boolean isChildren(Organ parentOrgan, Organ child);

}
