package com.apocalypse.idaas.service.single.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.*;
import com.apocalypse.idaas.module.single.*;
import com.apocalypse.idaas.service.single.ElementService;
import com.apocalypse.idaas.service.single.OrganService;
import com.apocalypse.idaas.service.single.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class OrganServiceImpl extends BaseServiceImpl<Organ, Long> implements OrganService {

    @Resource
    private OrganMapper organMapper;

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private TeamIdentityUnionMapper teamIdentityUnionMapper;

    @Resource
    private IdentityMapper identityMapper;

    @Resource
    private StaffMapper staffMapper;


    @Resource
    private AccountMapper accountMapper;

    @Resource
    private IdentityAccountUnionMapper identityAccountUnionMapper;

    @Resource
    private AccountRoleUnionMapper accountRoleUnionMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ElementService elementService;

    @Override
    public Long findTopOrganId() {
        Organ topOrgan = organMapper.selectOneByProperty(Organ::getParentId, 0L);
        return ObjectUtil.isNotNull(topOrgan) ? topOrgan.getId() : 0L;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOrgan(Organ organ, Identity identity) {

        organMapper.insertSelective(organ);

        return organ.getId();
    }

    @Override
    public void buildOrganTree(Organ topOrgan) {
        List<Organ> children = organMapper.selectByProperty(Organ::getParentId, topOrgan.getId());
        if (CollUtil.isNotEmpty(children)) {
            topOrgan.setChildren(children);
            children.forEach(this::buildOrganTree);
        }
    }

    @Override
    public List<Organ> findCurrentOrganAndChildren(Long organId, List<Organ> organs) {
        Organ currentOrgan = organMapper.selectByPrimaryKey(organId);
        if (CollUtil.isEmpty(organs)) {
            organs = new ArrayList<>();
            organs.add(currentOrgan);
        }

        List<Organ> children = organMapper.selectByProperty(Organ::getParentId, currentOrgan.getId());
        for (Organ organ : children) {
            organs.add(organ);
            findCurrentOrganAndChildren(organ.getId(), organs);
        }
        return organs;
    }

    @Override
    public boolean isChildren(Organ parentOrgan, Organ child) {
        List<Organ> children = findCurrentOrganAndChildren(parentOrgan.getId(), null);
        return children.stream().filter(organ -> organ.getId().equals(child.getId())).findAny().isPresent();
    }

}
