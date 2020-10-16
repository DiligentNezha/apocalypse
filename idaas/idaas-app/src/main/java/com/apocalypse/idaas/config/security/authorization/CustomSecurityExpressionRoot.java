package com.gkjx.saas.health.admin.config.security.authorization;

import cn.hutool.core.util.StrUtil;
import com.gkjx.saas.health.admin.config.security.SecurityConstants;
import com.gkjx.saas.health.admin.config.security.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/10
 */
@Slf4j
@Component
public class CustomSecurityExpressionRoot implements EnvironmentAware {

    public static Boolean permissionCheck = true;

    private List<String> profiles;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final List<String> whiteListPatterns = new ArrayList<>();

    static {
        whiteListPatterns.add("/permission/check/*");
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 真实请求路径
        String path = request.getRequestURI();

        // 认证主体
        Object principal = authentication.getPrincipal();

        String principalName = authentication.getName();

        // 当前认证身份拥有的授权集合
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> authorityPatterns = authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());

        log.info(StrUtil.format("当前环境{}请求路径为[{}]；认证主体[{}]；授权列表[{}]", profiles, path, principalName, authorityPatterns));

        for (String pattern : whiteListPatterns) {
            if (!isAnonymousUser(authentication) && antPathMatcher.match(pattern, path)) {
                return true;
            }
        }

        if (antPathMatcher.matchStart(SecurityConstants.MENU_TREE_PATH, path) && !isAnonymousUser(authentication)) {
            return true;
        }
        if (profiles.contains("dev") && !isAnonymousUser(authentication)) {
            return true;
        }

        if (profiles.contains("test") && !isAnonymousUser(authentication) && !permissionCheck) {
            return true;
        }

        boolean hasPermission = false;

        if (principal instanceof CustomUserDetails) {
            for (GrantedAuthority authority : authorities) {
                // 授权的路径模式
                String pattern = authority.getAuthority();
                hasPermission = antPathMatcher.match(pattern, path);
                if (hasPermission) {
                    break;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public void setEnvironment(Environment environment) {
        profiles = Arrays.asList(environment.getActiveProfiles());
    }

    public static List<String> getWhiteListPatterns() {
        return whiteListPatterns;
    }

    private boolean isAnonymousUser(Authentication authentication) {
        return "anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString());
    }
}
