package com.apocalypse.idaas.config.security.userdetails;

import com.apocalypse.common.core.module.AccountAware;
import com.apocalypse.common.core.module.OrganAware;
import com.apocalypse.common.core.module.IdentityAware;
import com.apocalypse.common.core.module.TeamAware;
import com.apocalypse.idaas.config.security.authorization.CustomGrantedAuthority;
import com.apocalypse.idaas.module.single.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/9
 */
public class CustomUserDetails implements UserDetails, IdentityAware, AccountAware, TeamAware, OrganAware {

    private Organ organ;

    private Identity identity;

    private Staff staff;

    private Account currentAccount;

    private List<Account> accounts;

    private List<Team> teams;

    private List<Role> currentRoles;

    private List<Element> currentElements;

    private List<CustomGrantedAuthority> currentAuthorities;

    public CustomUserDetails() {
        this.identity = new Identity();
        this.accounts = new ArrayList<>();
        this.currentRoles = new ArrayList<>();
        this.currentElements = new ArrayList<>();
        this.currentAuthorities = new ArrayList<>();

    }

    public CustomUserDetails(Organ organ, Identity identity, Staff staff, Account currentAccount, List<Account> accounts, List<Team> teams, List<Role> roles, List<Element> elements, List<CustomGrantedAuthority> currentAuthorities) {
        this.accounts = new ArrayList<>();
        this.currentRoles = new ArrayList<>();
        this.currentElements = new ArrayList<>();
        this.currentAuthorities = new ArrayList<>();
        this.teams = new ArrayList<>();

        this.currentAccount = currentAccount;

        this.organ = organ;
        this.identity = identity;
        this.staff = staff;

        this.accounts.addAll(accounts);
        this.teams.addAll(teams);
        this.currentRoles.addAll(roles);
        this.currentElements.addAll(elements);
        this.currentAuthorities.addAll(currentAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return currentAuthorities;
    }

    @Override
    public String getPassword() {
        return identity.getPassword();
    }

    @Override
    public String getUsername() {
        return identity.getLoginName();
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
        return identity.getEnabled();
    }

    @Override
    public Long currentStaffId() {
        return staff.getId();
    }

    @Override
    public Long currentIdentityId() {
        return identity.getId();
    }

    @Override
    public Long currentAccountId() {
        return currentAccount.getId();
    }

    @Override
    public Long currentOrganId() {
        return organ.getId();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Organ getOrgan() {
        return organ;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Staff getStaff() {
        return staff;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Role> getCurrentRoles() {
        return currentRoles;
    }

    public List<Element> getCurrentElements() {
        return currentElements;
    }

    public List<CustomGrantedAuthority> getCurrentAuthorities() {
        return currentAuthorities;
    }

    @Override
    public List<Long> currentTeamIds() {
        return teams.stream().mapToLong(Team::getId).boxed().collect(Collectors.toList());
    }

}
