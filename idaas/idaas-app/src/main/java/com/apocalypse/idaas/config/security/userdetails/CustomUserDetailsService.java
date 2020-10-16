package com.gkjx.saas.health.admin.config.security.userdetails;

import com.gkjx.saas.health.admin.config.security.authorization.CustomGrantedAuthority;
import com.gkjx.saas.health.admin.model.single.OpsStaff;
import com.gkjx.saas.health.admin.model.single.ops.*;
import com.gkjx.saas.health.admin.service.single.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/9
 */
@Primary
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private OpsIdentityService opsIdentityService;

    @Autowired
    private OpsStaffService opsStaffService;

    @Autowired
    private OpsRoleService opsRoleService;

    @Autowired
    private OpsAccountService opsAccountService;

    @Autowired
    private OpsResourceService opsResourceService;

    @Autowired
    private OpsElementService opsElementService;

    @Autowired
    private OpsIdentityAccountUnionService opsIdentityAccountUnionService;

    @Autowired
    private OpsAccountRoleUnionService opsAccountRoleUnionService;

    @Autowired
    private OpsRoleElementUnionService opsRoleElementUnionService;

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        OpsIdentity opsIdentity = opsIdentityService.selectOneByProperty(OpsIdentity::getLoginName, loginName);
        if (Objects.nonNull(opsIdentity)) {
            List<OpsIdentityAccountUnion> opsIdentityAccountUnions = opsIdentityAccountUnionService.selectByProperty(OpsIdentityAccountUnion::getOpsIdentityId, opsIdentity.getId());
            List<Long> opsAccountIds = opsIdentityAccountUnions.stream().mapToLong(OpsIdentityAccountUnion::getOpsAccountId).boxed().collect(Collectors.toList());

            List<OpsAccount> opsAccounts = opsAccountService.selectByIdList(opsAccountIds);

            opsAccounts.forEach(opsAccount -> {
                List<OpsAccountRoleUnion> opsAccountRoleUnions = opsAccountRoleUnionService.selectInByProperty(OpsAccountRoleUnion::getOpsAccountId, opsAccountIds);
                List<Long> opsRoleIds = opsAccountRoleUnions.stream().mapToLong(OpsAccountRoleUnion::getOpsRoleId).boxed().collect(Collectors.toList());
                List<OpsRole> opsRoles = opsRoleService.selectByIdList(opsRoleIds);
                opsAccount.setOpsRoles(opsRoles);

            });

            List<CustomGrantedAuthority> authorityPatterns = new ArrayList<>();

            List<OpsElement> opsElementList = new ArrayList<>();

            OpsAccount currentOpsAccount = opsAccounts.get(0);

            List<OpsRole> opsRoles = currentOpsAccount.getOpsRoles();

            List<OpsResource> currentResources = new ArrayList<>();

            opsRoles.stream().forEach(role -> {
                List<OpsRoleElementUnion> roleElements = opsRoleElementUnionService.selectByProperty(OpsRoleElementUnion::getOpsRoleId, role.getId());
                List<OpsElement> opsElements = opsElementService.selectByIdList(roleElements.stream().map(OpsRoleElementUnion::getOpsElementId).collect(Collectors.toList()));
                opsElementList.addAll(opsElements);

                List<Long> resourceIds = opsElements.stream().map(OpsElement::getOpsResourceId).collect(Collectors.toList());

                List<OpsResource> opsResources = opsResourceService.selectByIdList(resourceIds);
                role.setOpsResources(opsResources);
                authorityPatterns.addAll(opsResources.stream().map(opsResource -> new CustomGrantedAuthority(opsResource.getPath())).collect(Collectors.toList()));
            });

            opsElementList.stream().forEach((element) -> {
                Optional<OpsResource> optionalOpsResource = currentResources.stream().filter(resource -> element.getOpsResourceId().equals(resource.getId())).findFirst();
                if (optionalOpsResource.isPresent()) {
                    element.setOpsResource(optionalOpsResource.get());
                }
            });

            OpsStaff opsStaff = opsStaffService.selectByPrimaryKey(opsIdentity.getStaffId());

            return new CustomUserDetails(opsIdentity, opsStaff, currentOpsAccount, opsAccounts, opsRoles, opsElementList, authorityPatterns);
        } else {
            // 员工不存在
            throw new UsernameNotFoundException("登录名不存在");
        }
    }

}
