package com.apocalypse.cms.oauth2.client.registrations.dingtalk;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/20
 */
public class DingTalkOAuth2User implements OAuth2User {

    private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

    private Map<String, Object> attributes;

    private String nick;

    private String openId;

    private String unionId;

    @Override
    public Map<String, Object> getAttributes() {
        if (ObjectUtil.isNull(attributes)) {
            this.attributes = new HashMap<>();
            this.attributes.put("nick", this.getNick());
            this.attributes.put("openId", this.getOpenId());
            this.attributes.put("unionId", this.getUnionId());
        }
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return nick;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
