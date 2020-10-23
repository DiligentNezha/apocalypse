package com.apocalypse.cms.api.response;

import com.apocalypse.common.core.api.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.Authentication;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/23
 */
@Data
@ApiModel
@Accessors(chain = true)
public class OAuth2MeResponse extends BaseResponse {
    @ApiModelProperty(value = "授权服务商", example = "钉钉")
    private String clientName;

    @ApiModelProperty(value = "身份", example = "admin1")
    private Authentication authentication;

}
