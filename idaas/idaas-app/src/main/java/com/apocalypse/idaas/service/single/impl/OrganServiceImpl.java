package com.gkjx.saas.health.system.service.single.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.api.response.OrganDismissStaffTreeResponse;
import com.gkjx.saas.health.system.mapper.single.*;
import com.gkjx.saas.health.system.model.single.*;
import com.gkjx.saas.health.system.service.single.ElementService;
import com.gkjx.saas.health.system.service.single.OrganService;
import com.gkjx.saas.health.system.service.single.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
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

    @Override
    public OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode findCurrentOrganAndChildrenDismissStaff(Long organId) {
        Organ currentOrgan = organMapper.selectByPrimaryKey(organId);
        OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode organNode = new OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode();
        organNode.setKey(currentOrgan.getId())
                .setTitle(currentOrgan.getName())
                .setNodeType(currentOrgan.getOrganType());

        List<Staff> staffInTeams = new ArrayList<>();

        List<Team> teams = teamMapper.selectByProperty(Team::getOrganId, organId);
        List<OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode> teamNodes = teams.stream().map(team -> {
            OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode teamNode = new OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode();
            teamNode.setKey(team.getId())
                    .setTitle(team.getName())
                    .setNodeType(20);
            List<TeamIdentityUnion> teamIdentityUnions = teamIdentityUnionMapper.selectByProperty(TeamIdentityUnion::getTeamId, team.getId());
            List<Long> identityIds = teamIdentityUnions.stream().mapToLong(TeamIdentityUnion::getIdentityId).boxed().collect(Collectors.toList());
            if (CollUtil.isNotEmpty(identityIds)) {
                List<Long> staffIds = identityMapper.selectByIdList(identityIds).stream().mapToLong(Identity::getStaffId).boxed().collect(Collectors.toList());
                List<Staff> staffInTeam = staffMapper.selectByIdList(staffIds);

                staffInTeams.addAll(staffInTeam);

                List<OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode> staffNodes = staffInTeam.stream().filter(Staff::getIsLeaveOffice).map(staff -> {
                    OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode staffNode = new OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode();
                    staffNode.setKey(staff.getId())
                            .setTitle(staff.getName())
                            .setNodeType(25);
                    return staffNode;
                }).collect(Collectors.toList());
                teamNode.setChildren(staffNodes);
            }
            return teamNode;
        }).collect(Collectors.toList());

        List<Staff> staffInOrgan = staffMapper.selectByProperty(Staff::getOrganId, organId);
        staffInOrgan.removeAll(staffInTeams);
        List<OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode> staffNodes = staffInOrgan.stream().filter(staff -> {
            if (staff.getIsLeaveOffice()) {
                Identity identity = identityMapper.selectOneByProperty(Identity::getStaffId, staff.getId());
                // 不存在，说明离职后又复职了
                return !identityAccountUnionMapper.existsWithProperty(IdentityAccountUnion::getIdentityId, identity.getId());
            }
            return false;
        }).map(staff -> {
            OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode staffNode = new OrganDismissStaffTreeResponse.OrganDismissStaffTreeNode();
            staffNode.setKey(staff.getId())
                    .setTitle(staff.getName())
                    .setNodeType(25);
            return staffNode;
        }).collect(Collectors.toList());
        teamNodes.addAll(staffNodes);
        organNode.setChildren(teamNodes);

        return organNode;
    }


}
