package com.apocalypse.idaas.config.security.userdetails;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.idaas.config.security.authorization.CustomGrantedAuthority;
import com.apocalypse.idaas.module.single.*;
import com.apocalypse.idaas.service.single.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private OrganService organService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ElementService elementService;

    @Autowired
    private TeamIdentityUnionService teamIdentityUnionService;

    @Autowired
    private IdentityAccountUnionService identityAccountUnionService;

    @Autowired
    private AccountRoleUnionService accountRoleUnionService;

    @Autowired
    private RoleElementUnionService roleElementUnionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @PostConstruct
    public void init() {
        Long topOrganId = organService.findTopOrganId();
        if (topOrganId.equals(0L)) {
            // 没有数据，首次运行
            Snowflake snowflake = IdUtil.getSnowflake(1, 5);
            Long organId = snowflake.nextId();
            Long staffId = snowflake.nextId();
            Long identityId = snowflake.nextId();
            Long accountId = snowflake.nextId();
            Long roleLabelId = snowflake.nextId();
            Long roleId = snowflake.nextId();

            String organInsertSQL = "INSERT INTO `organ`(`id`, `parent_id`, `serial_number`, `name`, `enabled`, `organ_type`, `order_num`, `address`, `contact`, `contact_mobile`, `email`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, 0, '{}', '虚拟根节点', 1, 1, 1, '北京市/市辖区/朝阳区/', '王涛', '15936055170', '', 0, 1271007558261411841, 1293385366287552512, 1271007558261411840, 1293385366337884160)";
            jdbcTemplate.execute(StrUtil.format(organInsertSQL, organId, IdUtil.fastSimpleUUID()));

            String staffInsertSQL = "INSERT INTO `staff`(`id`, `organ_id`, `staff_identity`, `mobile`, `email`, `name`, `gender`, `id_card`, `address`, `urgency_contact`, `urgency_contact_mobile`, `is_leave_office`, `enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, 'superman', '12345678988', '', '', 3, '', '', '', '', 0, 1, 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(staffInsertSQL, organId, staffId));

            String identityInsertSQL = "INSERT INTO `identity`(`id`, `organ_id`, `staff_id`, `login_name`, `mobile`, `email`, `password`, `enabled`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, {}, '{}', '12345678988', '', '{}', 1, 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(identityInsertSQL, identityId, organId, staffId, "root", passwordEncoder.encode("superman")));

            String accountInsertSQl = "INSERT INTO `account`(`id`, `organ_id`, `account_name`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, 'superman_account_001', 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(accountInsertSQl, accountId, organId));

            String identityAccountInsertSQL = "INSERT INTO `identity_account_union`(`id`, `identity_id`, `account_id`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, {}, 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(identityAccountInsertSQL, snowflake.nextId(), identityId, accountId));

            String roleLabelInsertSQL = "INSERT INTO `role_label`(`id`, `organ_id`, `name`, `remark`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, '默认', '', 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(roleLabelInsertSQL, roleLabelId, organId));

            String roleInsertSQL = "INSERT INTO `role`(`id`, `organ_id`, `role_label_id`, `name`, `remark`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, {}, '数据同步管理员'`, '', 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(roleInsertSQL, roleId, organId, roleLabelId));

            String accountRoleInsertSQL = "INSERT INTO `account_role_union`(`id`, `account_id`, `role_id`, `is_deleted`, `create_identity_id`, `update_identity_id`, `create_account_id`, `update_account_id`) VALUES ({}, {}, {}, 0, 1271007558261411841, 1271007558261411841, 1271007558261411840, 1271007558261411840)";
            jdbcTemplate.execute(StrUtil.format(accountRoleInsertSQL, snowflake.nextId(), accountId, roleId));
        }

    }

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Identity identity = identityService.selectOneByProperty(Identity::getLoginName, loginName);
        if (Objects.nonNull(identity)) {
            Long organId = identity.getOrganId();
            List<IdentityAccountUnion> identityAccountUnions = identityAccountUnionService.selectByProperty(IdentityAccountUnion::getIdentityId, identity.getId());
            List<Long> accountIds = identityAccountUnions.stream().mapToLong(IdentityAccountUnion::getAccountId).boxed().collect(Collectors.toList());

            List<Account> accounts = accountService.selectByIdList(accountIds);

            accounts.forEach(account -> {
                List<AccountRoleUnion> accountRoleUnions = accountRoleUnionService.selectByProperty(AccountRoleUnion::getAccountId, account.getId());
                List<Long> roleIds = accountRoleUnions.stream().mapToLong(AccountRoleUnion::getRoleId).boxed().collect(Collectors.toList());
                List<Role> roles = roleService.selectByIdList(roleIds);
                account.setRoles(roles);
            });

            List<CustomGrantedAuthority> authorityPatterns = new ArrayList<>();

            List<Element> elementList = new ArrayList<>();

            Account currentAccount = accounts.get(0);
            List<Role> roles = currentAccount.getRoles();

            List<Resource> currentResources = new ArrayList<>();

            roles.stream().forEach(role -> {
                List<RoleElementUnion> roleElements = roleElementUnionService.selectByProperty(RoleElementUnion::getRoleId, role.getId());
                List<Element> elements = elementService.selectByIdList(roleElements.stream().map(RoleElementUnion::getElementId).collect(Collectors.toList()));
                elementList.addAll(elements);

                List<Long> resourceIds = elements.stream().map(Element::getResourceId).collect(Collectors.toList());

                List<Resource> resources = resourceService.selectByIdList(resourceIds);
                currentResources.addAll(resources);
                role.setResources(resources);
                authorityPatterns.addAll(resources.stream().map(resource -> new CustomGrantedAuthority(resource.getPath())).collect(Collectors.toList()));
            });

            elementList.stream().forEach((element) -> {
                Optional<Resource> optionalResource = currentResources.stream().filter(resource -> element.getResourceId().equals(resource.getId())).findFirst();
                if (optionalResource.isPresent()) {
                    element.setResource(optionalResource.get());
                }
            });

            Staff staff = staffService.selectByPrimaryKey(identity.getStaffId());
            Organ organ = organService.selectByPrimaryKey(organId);

            List<TeamIdentityUnion> teamIdentityUnions = teamIdentityUnionService.selectByProperty(TeamIdentityUnion::getIdentityId, identity.getId());
            List<Long> groupIds = teamIdentityUnions.stream().mapToLong(TeamIdentityUnion::getIdentityId).boxed().collect(Collectors.toList());
            List<Team> teams = teamService.selectByIdList(groupIds);
            return new CustomUserDetails(organ, identity, staff, currentAccount, accounts, teams, roles, elementList, authorityPatterns);
        } else {
            // 员工不存在
            throw new UsernameNotFoundException("员工名不存在");
        }
    }

}
