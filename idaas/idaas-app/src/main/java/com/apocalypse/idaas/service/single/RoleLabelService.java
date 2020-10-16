package com.gkjx.saas.health.system.service.single;

import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.RoleLabel;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface RoleLabelService extends BaseService<RoleLabel, Long> {

    List<RoleLabel> selectAllRoleLabels();

    /**
     * 查找虚拟机构节点的默认角色标签 ID（管理端）
     * @return
     */
    Long findDefaultRoleLabelId();

    /**
     * 查找机构默认的角色标签 ID（医生端）
     * @return
     */
    Long findDefaultRoleLabelIdOrganId(Long organId);
}
