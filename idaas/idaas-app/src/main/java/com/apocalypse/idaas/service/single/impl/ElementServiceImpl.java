package com.apocalypse.idaas.service.single.impl;

import cn.hutool.core.collection.CollUtil;
import com.apocalypse.common.core.util.TreeNodeUtil;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.AccountRoleUnionMapper;
import com.apocalypse.idaas.mapper.single.ElementMapper;
import com.apocalypse.idaas.mapper.single.ResourceMapper;
import com.apocalypse.idaas.mapper.single.RoleElementUnionMapper;
import com.apocalypse.idaas.module.single.AccountRoleUnion;
import com.apocalypse.idaas.module.single.Element;
import com.apocalypse.idaas.module.single.RoleElementUnion;
import com.apocalypse.idaas.service.single.ElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class ElementServiceImpl extends BaseServiceImpl<Element, Long> implements ElementService {

    @Resource
    private ElementMapper elementMapper;

    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private AccountRoleUnionMapper accountRoleUnionMapper;

    @Resource
    private RoleElementUnionMapper roleElementUnionMapper;

    @Override
    public List<Element> buildElementTreeByAccountId(Long accountId) {
        List<AccountRoleUnion> accountRoleUnions = accountRoleUnionMapper.selectByProperty(AccountRoleUnion::getAccountId, accountId);
        List<Long> roleIds = accountRoleUnions.stream().mapToLong(AccountRoleUnion::getRoleId).boxed().collect(Collectors.toList());

        if (CollUtil.isEmpty(roleIds)) {
//            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.ACCOUNT_NO_ROLE, accountId);
        }

        List<RoleElementUnion> roleElements = roleElementUnionMapper.selectInByProperty(RoleElementUnion::getRoleId, roleIds);

        List<Long> elementIds = roleElements.stream().mapToLong(RoleElementUnion::getElementId).boxed().collect(Collectors.toList());

        List<Element> elements = elementMapper.selectByIdList(elementIds);

        // 填充 Resource 属性
        elements.stream().forEach(this::fillResource);

        // 将元素列表转换成树结构
        TreeNodeUtil.toTree(elements);

        return elements;
    }

    @Override
    public void fillResource(Element element) {
        Long resourceId = element.getResourceId();
        if (resourceId > 0) {
            com.apocalypse.idaas.module.single.Resource resource = resourceMapper.selectByPrimaryKey(resourceId);
            element.setResource(resource);
        }
    }
}
