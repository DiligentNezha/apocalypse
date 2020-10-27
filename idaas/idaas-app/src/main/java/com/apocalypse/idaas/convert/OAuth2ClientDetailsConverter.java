package com.apocalypse.idaas.convert;

import com.apocalypse.common.util.json.JsonUtil;
import com.apocalypse.idaas.config.security.oauth2.client.CustomClientDetails;
import com.apocalypse.idaas.model.AdminDO;
import com.apocalypse.idaas.module.single.OAuthClientDetails;
import com.apocalypse.idaas.vo.LoginVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.redisson.misc.Hash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/14
 */
@Mapper(componentModel = "spring")
public interface OAuth2ClientDetailsConverter {

    @Mappings({
            @Mapping(source = "scope", target = "scope", qualifiedByName = "scopeMap" ),
            @Mapping(source = "resourceIds", target = "resourceIds", qualifiedByName = "resourceIdsMap" ),
            @Mapping(source = "authorizedGrantTypes", target = "authorizedGrantTypes", qualifiedByName = "authorizedGrantTypesMap" ),
            @Mapping(source = "autoApprove", target = "autoApproveScopes", qualifiedByName = "autoApproveScopesMap" ),
            @Mapping(source = "webServerRedirectUri", target = "registeredRedirectUri", qualifiedByName = "registeredRedirectUrisMap" ),
            @Mapping(source = "authorities", target = "authorities", qualifiedByName = "authoritiesMap" ),
            @Mapping(source = "additionalInformation", target = "additionalInformation", qualifiedByName = "additionalInformationMap" ),
    })
    CustomClientDetails convert(OAuthClientDetails oAuthClientDetails);

    @Named("scopeMap")
    default Set<String> scopeMap(ArrayNode scopesArrayNode) {
        Set<String> scopes = new HashSet<>();
        scopesArrayNode.iterator().forEachRemaining(scope -> {
            scopes.add(scope.asText());
        });
        return scopes;
    }

    //    additionalInformation

//    resourceIds
//            authorizedGrantTypes
//    registeredRedirectUris
//            autoApproveScopes

    @Named("resourceIdsMap")
    default Set<String> resourceIdsMap(ArrayNode resourceIdsArrayNode) {
        Set<String> resourceIds = new HashSet<>();
        resourceIdsArrayNode.iterator().forEachRemaining(resourceId -> {
            resourceIds.add(resourceId.asText());
        });
        return resourceIds;
    }

    @Named("authorizedGrantTypesMap")
    default Set<String> authorizedGrantTypesMap(ArrayNode authorizedGrantTypesArrayNode) {
        Set<String> authorizedGrantTypes = new HashSet<>();
        authorizedGrantTypesArrayNode.iterator().forEachRemaining(authorizedGrantType -> {
            authorizedGrantTypes.add(authorizedGrantType.asText());
        });
        return authorizedGrantTypes;
    }

    @Named("autoApproveScopesMap")
    default Set<String> autoApproveScopesMap(ArrayNode autoApproveScopesArrayNode) {
        Set<String> authorizedGrantTypes = new HashSet<>();
        autoApproveScopesArrayNode.iterator().forEachRemaining(autoApproveScope -> {
            authorizedGrantTypes.add(autoApproveScope.textValue());
        });
        return authorizedGrantTypes;
    }

    @Named("registeredRedirectUrisMap")
    default Set<String> registeredRedirectUrisMap(String webServerRedirectUri) {
        Set<String> redirectUri = new HashSet<>();
        redirectUri.add(webServerRedirectUri);
        return redirectUri;
    }

    @Named("additionalInformationMap")
    default Map<String, Object> additionalInformationMap(ObjectNode additionalInformationObjectNode) {
        LinkedHashMap additionalInformation = JsonUtil.toObj(additionalInformationObjectNode.toPrettyString(), LinkedHashMap.class);
        return additionalInformation;
    }

    @Named("authoritiesMap")
    default List<GrantedAuthority> authoritiesMap(ArrayNode authoritiesArrayNode) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authoritiesArrayNode.iterator().forEachRemaining(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.asText()));
        });
        return authorities;
    }

}
