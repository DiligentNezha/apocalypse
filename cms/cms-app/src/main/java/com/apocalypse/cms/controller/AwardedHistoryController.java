package com.apocalypse.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.core.enums.error.BusinessErrorCodeEnum;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.apocalypse.cms.model.single.AwardedHistory;
import com.apocalypse.cms.service.single.AwardedHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 双色排列池(AwardedHistory)表控制层
 *
 * @author makejava
 * @since 2021-03-07 12:07:13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/ball/history")
@Api(value = "AwardedHistory", tags = {"AwardedHistory"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AwardedHistoryController {

    @Autowired
    private AwardedHistoryService awardedHistoryService;

    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "添加", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> add(@Validated @RequestBody AwardedHistory request) {
        awardedHistoryService.insertSelective(request);
        return Rest.vector("id", request.getId(), Long.class);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> update(@Validated @RequestBody AwardedHistory request) {
        Long id = request.getId();
        AwardedHistory awardedHistory = awardedHistoryService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(awardedHistory)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        awardedHistoryService.updateByPrimaryKeySelective(request);
        return Rest.vector("id", request.getId(), Long.class);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "详情", notes = "详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> detail(@PathVariable Long id) {
        AwardedHistory awardedHistory = awardedHistoryService.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(awardedHistory)) {
            throw ServiceExceptionUtil.fail(BusinessErrorCodeEnum.RECORD_NOT_FOUND, id);
        }
        return Rest.vector("detail", awardedHistory, AwardedHistory.class);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> delete(@PathVariable Long id) {
        awardedHistoryService.deleteByPrimaryKey(id);
        return Rest.vector("deleted", true, Boolean.class);
    }

}
