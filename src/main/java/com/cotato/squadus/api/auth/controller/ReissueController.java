package com.cotato.squadus.api.auth.controller;

import com.cotato.squadus.common.config.jwt.JWTUtil;
import com.cotato.squadus.common.config.jwt.RefreshRepository;
import com.cotato.squadus.domain.auth.service.RefreshService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@Tag(name = "토큰 재발급", description = "Access Token 재발급 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshService refreshService;


    @PostMapping("/reissue")
    @Operation(summary = "Access token 재발급", description = "Refresh token을 바탕으로 Access token을 재발급합니다")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    log.info("Refresh cookie found");
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            //response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> map = refreshService.reissueRefreshToken(refresh);

        //response
        String newAccess = map.get("access");
        response.setHeader("access", newAccess);
        String newRefresh = map.get("refresh");
        response.addCookie(refreshService.createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
