package com.apocalypse.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.core.module.AccountAware;
import com.apocalypse.common.core.module.OrganAware;
import com.apocalypse.common.core.module.StaffAware;
import com.apocalypse.common.core.module.TeamAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/9
 */
public class ContextHolder {

    public static Long currentStaffId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtil.isNotNull(principal) && principal instanceof StaffAware) {
            return ((StaffAware) principal).currentStaffId();
        }
        return 0L;
    }

    public static Long currentIdentityId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtil.isNotNull(principal) && principal instanceof StaffAware) {
            return ((StaffAware) principal).currentIdentityId();
        }
        return 0L;
    }

    public static Long currentAccountId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtil.isNotNull(principal) && principal instanceof AccountAware) {
            return ((AccountAware) principal).currentAccountId();
        }
        return 0L;
    }

    public static Long currentOrganId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtil.isNotNull(principal) && principal instanceof OrganAware) {
            return ((OrganAware) principal).currentOrganId();
        }
        return 0L;
    }

    public static List<Long> currentTeamIds() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtil.isNotNull(principal) && principal instanceof TeamAware) {
            return ((TeamAware) principal).currentTeamIds();
        }
        return new ArrayList<>();
    }

}
