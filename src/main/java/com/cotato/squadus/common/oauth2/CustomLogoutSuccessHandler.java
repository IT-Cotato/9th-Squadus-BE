package com.cotato.squadus.common.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("onLogoutSuccess 실행");
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);  // 쿠키 삭제
        cookie.setPath("/");  // 모든 경로에서 적용
        response.addCookie(cookie);

        response.sendRedirect("/");  // 로그아웃 성공 후 리디렉션
    }

}