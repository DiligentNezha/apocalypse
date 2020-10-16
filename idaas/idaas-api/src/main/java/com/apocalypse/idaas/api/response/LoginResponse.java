package com.gkjx.saas.health.system.api.response;

import com.gkjx.common.core.api.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/7/6
 */
@Data
@ApiModel
@Accessors(chain = true)
public class LoginResponse extends BaseResponse {

    @ApiModelProperty(value = "登录名", example = "admin1")
    private String loginName;

    @ApiModelProperty(value = "当前账号所属机构 ID", example = "1293385365155090432")
    private Long organId;

    @ApiModelProperty(value = "当前账户 ID", example = "1293385366337884160")
    private Long currentAccountId;

    @ApiModelProperty(value = "当前登录账号拥有的账户列表")
    private List<Long> accountIds;

}
