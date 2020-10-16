package com.gkjx.saas.health.admin.config.security.authorization;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/10
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    /**
     * 资源路径
     */
    private String resourcePath;

    public CustomGrantedAuthority() {
    }

    public CustomGrantedAuthority(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String getAuthority() {
        return resourcePath;
    }
}
