package com.cotato.squadus.common.dto;

import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@Slf4j
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String providerId;


    public static OAuth2Attribute of(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equals("google")){
            return ofGoogle(attributes);
        }
        return ofNaver(attributes);
    }


    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .providerId(attributes.get("sub").toString())
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofNaver(Map<String, Object> attributes) {
        Map<String, Object> attributesMap = (Map<String, Object>) attributes.get("response");
        log.info(attributesMap.toString());

        return OAuth2Attribute.builder()
                .name(attributesMap.get("name").toString())
                .email(attributesMap.get("email").toString())
                .providerId(attributesMap.get("id").toString())
                .attributes(attributesMap)
                .build();
    }

    public Member toEntity(String uniqueId){
        return Member.builder()
                .username(name)
                .email(email)
                .memberId(uniqueId)
                .memberRole(MemberRole.MEMBER)
                .build();
    }

}