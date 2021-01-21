package com.apocalypse.cms.api.response;

import com.apocalypse.common.core.api.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

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

    @ApiModelProperty(value = "登录名", example = "root")
    private String loginName;

    @ApiModelProperty(value = "属性")
    private Map<String, Object> attributes;

    @ApiModelProperty(value = "权限")
    private Collection<GrantedAuthority> authorities;
}
