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
        } else if(registrationId.equals("kakao")){
            return ofKakao(attributes);
        }
        return ofNaver(attributes);
    }

    private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");  // 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값을 꺼낸다.
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");   // 마찬가지로 profile(nickname, image_url.. 등) 정보가 담긴 값을 꺼낸다.

        return OAuth2Attribute.builder()
                .name(profile.get("nickname").toString())
                .email(kakao_account.get("email").toString())
                .providerId(attributes.get("id").toString())
                .attributes(attributes)
                .build();
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