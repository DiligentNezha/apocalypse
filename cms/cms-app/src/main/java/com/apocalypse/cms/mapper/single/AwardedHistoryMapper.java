package com.apocalypse.cms.mapper.single;

import com.apocalypse.cms.model.single.AwardedHistory;
import com.apocalypse.common.data.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 双色排列池(AwardedHistory)表数据库访问层
 *
 * @author makejava
 * @since 2021-03-07 12:07:14
 */
public interface AwardedHistoryMapper extends MyMapper<AwardedHistory, Long> {

}
