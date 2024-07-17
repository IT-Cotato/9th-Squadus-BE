package com.cotato.squadus.domain.auth.service;

import com.cotato.squadus.common.config.jwt.JWTUtil;
import com.cotato.squadus.common.config.jwt.RefreshEntity;
import com.cotato.squadus.common.config.jwt.RefreshRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshService {

    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    public Map<String, String> reissueRefreshToken(String refresh){
        Map<String, String> map = new HashMap<>();

        String uniqueId = jwtUtil.getUniqueId(refresh);
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", uniqueId, username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", uniqueId, username, role, 86400000L);
        map.put("access", newAccess);
        map.put("refresh", newRefresh);

        log.info("New refresh: " + newRefresh + " access: " + newAccess);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(uniqueId, username, newRefresh, 86400000L);

        return map;
    }

    public void addRefreshEntity(String uniqueId, String username, String refresh, Long expiredMs) {
        //증복 저장x 구현해야함
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUniqueId(uniqueId);
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    public Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");  //모든 위치에서 쿠키를 볼 수 있음
        cookie.setHttpOnly(true); //자바스크립트가 쿠키를 가져가지 못하게 함

        return cookie;
    }

}
