package com.apocalypse.cms.oauth2.client.registrations.idaas;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class IdaasOAuth2User implements OAuth2User {

    private Set<GrantedAuthority> authorities;

    private Map<String, Object> attributes;

    private String username;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    {
        authorities = new LinkedHashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (ObjectUtil.isNull(attributes)) {
            this.attributes = new HashMap<>();
            this.attributes.put("username", this.getUsername());
            this.attributes.put("enabled", this.getEnabled());
            this.attributes.put("accountNotExpired", this.getAccountNonExpired());
            this.attributes.put("accountNonLocked", this.getAccountNonLocked());
            this.attributes.put("credentialsNonExpired", this.getCredentialsNonExpired());
        }
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
