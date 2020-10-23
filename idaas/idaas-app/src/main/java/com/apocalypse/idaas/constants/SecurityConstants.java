package com.apocalypse.idaas.constants;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/28
 */
public class SecurityConstants {
    public static final String AUTH_CAPTCHA = "/idaas/login/captcha/{uuid}";

//    public static final String LOGIN_PROCESSING_URL = "/login/json";
    public static final String LOGIN_PROCESSING_URL = "/idaas/auth/oauth/authorize";

    public static final String AUTH_LOGOUT = "/idaas/logout";

    public static final String MENU_TREE_PATH = "/idaas/system/menu/tree";
}
