package com.cotato.squadus.common.oauth2;

import com.cotato.squadus.common.dto.LoginDto;
import com.cotato.squadus.common.dto.OAuth2Attribute;
import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.domain.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //naver, google, kakao
        OAuth2Attribute attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());

        String uniqueId = registrationId+" "+attribute.getProviderId();

        Member member = saveOrUpdate(attribute, uniqueId);
        LoginDto loginDto = new LoginDto(member);

        log.info("CustomOAuth2UserService loadUser: {}", loginDto);
        return new CustomOAuth2User(loginDto);
    }

    private Member saveOrUpdate(OAuth2Attribute attribute, String memberId){
        Member member = memberRepository.findByMemberId(memberId)
                .map(entity -> entity.update(attribute.getEmail(), attribute.getName()))
                .orElse(attribute.toEntity(memberId));

        log.info("CustomOAuth2UserService saveOrUpdate: {}", member);
        return memberRepository.save(member);
    }
}