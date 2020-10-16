package com.gkjx.saas.health.admin.config.security.userdetails;

import com.gkjx.common.core.module.AccountAware;
import com.gkjx.common.core.module.StaffAware;
import com.gkjx.saas.health.admin.config.security.authorization.CustomGrantedAuthority;
import com.gkjx.saas.health.admin.model.single.OpsStaff;
import com.gkjx.saas.health.admin.model.single.ops.OpsAccount;
import com.gkjx.saas.health.admin.model.single.ops.OpsElement;
import com.gkjx.saas.health.admin.model.single.ops.OpsRole;
import com.gkjx.saas.health.admin.model.single.ops.OpsIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/9
 */
public class CustomUserDetails implements UserDetails, StaffAware, AccountAware {

    private OpsIdentity opsIdentity;

    private OpsStaff opsStaff;

    private List<OpsAccount> opsAccounts;

    private OpsAccount currentOpsAccount;

    private List<OpsRole> currentOpsRoles;

    private List<OpsElement> currentOpsElements;

    private List<CustomGrantedAuthority> currentAuthorities;

    public CustomUserDetails() {
        this.opsIdentity = new OpsIdentity();
        this.opsAccounts = new ArrayList<>();
        this.currentOpsRoles = new ArrayList<>();
        this.currentOpsElements = new ArrayList<>();
        this.currentAuthorities = new ArrayList<>();

    }

    public CustomUserDetails(OpsIdentity opsIdentity, OpsStaff opsStaff, OpsAccount currentOpsAccount, List<OpsAccount> opsAccounts, List<OpsRole> currentOpsRoles, List<OpsElement> currentOpsElements, List<CustomGrantedAuthority> currentAuthorities) {
        this.opsAccounts = new ArrayList<>();
        this.currentOpsRoles = new ArrayList<>();
        this.currentOpsElements = new ArrayList<>();
        this.currentAuthorities = new ArrayList<>();

        this.currentOpsAccount = currentOpsAccount;

        this.opsIdentity = opsIdentity;
        this.opsStaff = opsStaff;

        this.opsAccounts.addAll(opsAccounts);
        this.currentOpsRoles.addAll(currentOpsRoles);
        this.currentOpsElements.addAll(currentOpsElements);
        this.currentAuthorities.addAll(currentAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return currentAuthorities;
    }

    @Override
    public String getPassword() {
        return opsIdentity.getPassword();
    }

    @Override
    public String getUsername() {
        return opsIdentity.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return opsIdentity.getEnabled();
    }

    @Override
    public Long currentStaffId() {
        return opsStaff.getId();
    }

    @Override
    public Long currentIdentityId() {
        return opsIdentity.getId();
    }

    @Override
    public Long currentAccountId() {
        return currentOpsAccount.getId();
    }

}
