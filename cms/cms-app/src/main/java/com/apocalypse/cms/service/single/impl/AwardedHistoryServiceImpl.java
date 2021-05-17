package com.apocalypse.cms.service.single.impl;

import com.apocalypse.cms.model.single.AwardedHistory;
import com.apocalypse.cms.mapper.single.AwardedHistoryMapper;
import com.apocalypse.cms.service.single.AwardedHistoryService;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * 双色排列池(AwardedHistory)表服务实现类
 *
 * @author makejava
 * @since 2021-03-07 12:07:12
 */
@Slf4j
@Service
public class AwardedHistoryServiceImpl extends BaseServiceImpl<AwardedHistory, Long> implements AwardedHistoryService {

    @Resource
    private AwardedHistoryMapper awardedHistoryMapper;
}
