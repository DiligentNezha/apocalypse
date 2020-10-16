package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.enums.PlatformEnum;
import com.gkjx.saas.health.system.mapper.single.RoleLabelMapper;
import com.gkjx.saas.health.system.model.single.RoleLabel;
import com.gkjx.saas.health.system.service.single.OrganService;
import com.gkjx.saas.health.system.service.single.RoleLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class RoleLabelServiceImpl extends BaseServiceImpl<RoleLabel, Long> implements RoleLabelService {

    @Resource
    private RoleLabelMapper roleLabelMapper;

    @Resource
    private OrganService organService;

    @Override
    public List<RoleLabel> selectAllRoleLabels() {
        return roleLabelMapper.selectAllRoleLabels();
    }

    @Override
    public Long findDefaultRoleLabelId() {
        Long topOrganId = organService.findTopOrganId();
        Weekend<RoleLabel> weekend = Weekend.of(RoleLabel.class);
        WeekendCriteria<RoleLabel, Object> weekendCriteria = weekend.weekendCriteria();
        weekendCriteria.andEqualTo(RoleLabel::getOrganId, topOrganId);
        weekendCriteria.andEqualTo(RoleLabel::getName, "默认");
        weekendCriteria.andEqualTo(RoleLabel::getPlatform, PlatformEnum.DOCTOR_MANAGE.getDbValue());

        RoleLabel defaultRoleLabel = roleLabelMapper.selectOneByExample(weekend);
        return defaultRoleLabel.getId();
    }

    @Override
    public Long findDefaultRoleLabelIdOrganId(Long organId) {
        Weekend<RoleLabel> weekend = Weekend.of(RoleLabel.class);
        WeekendCriteria<RoleLabel, Object> weekendCriteria = weekend.weekendCriteria();
        weekendCriteria.andEqualTo(RoleLabel::getOrganId, organId);
        weekendCriteria.andEqualTo(RoleLabel::getName, "岗位");

        RoleLabel defaultRoleLabel = roleLabelMapper.selectOneByExample(weekend);
        return defaultRoleLabel.getId();
    }
}
