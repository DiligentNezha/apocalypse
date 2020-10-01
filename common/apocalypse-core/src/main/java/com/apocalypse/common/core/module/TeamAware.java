package com.apocalypse.common.core.module;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/25
 */
public interface TeamAware {

    /**
     * 当前身份拥有的团队 ID 集合
     * @return
     */
    List<Long> currentTeamIds();
}
