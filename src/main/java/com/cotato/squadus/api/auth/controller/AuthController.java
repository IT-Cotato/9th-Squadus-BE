package com.cotato.squadus.api.auth.controller;

import com.cotato.squadus.domain.auth.service.AuthService;
import com.cotato.squadus.api.auth.dto.JoinRequest;
import com.cotato.squadus.api.auth.dto.LoginRequest;
import com.cotato.squadus.domain.auth.entity.Member;
import com.cotato.squadus.common.config.jwt.RefreshEntity;
import com.cotato.squadus.common.config.jwt.JWTUtil;
import com.cotato.squadus.domain.auth.repository.MemberRepository;
import com.cotato.squadus.domain.auth.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    @PostMapping("/join")
    public ResponseEntity<Void> joinProcess(@RequestBody JoinRequest joinRequest) {
        log.info("회원가입 컨트롤러 : {}, {}", joinRequest.getEmail(), joinRequest.getUsername());
        authService.joinProcess(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//        Member member = memberRepository.findByUsername(loginRequest.getUsername());
//
//        if (member == null || !member.getEmail().equals(loginRequest.getEmail())) {
//            return ResponseEntity.status(401).build();
//        }
//
//        String memberId = member.getMemberId();
//        String role = member.getRoleKey();
//        System.out.println("role = " + role);
//
//        //토큰 생성
//        String access = jwtUtil.createJwt("access", memberId, role, 600000L);
//        String refresh = jwtUtil.createJwt("refresh", memberId, role, 86400000L);
//
//        addRefreshEntity(memberId, refresh, 86400000L);
//
//        //응답 설정
//        response.setHeader("access", access);
//        response.setHeader("refresh", refresh);
//        response.setStatus(HttpStatus.OK.value());
////        LoginResponse loginResponse = new LoginResponse(access, refresh);
//        return ResponseEntity.ok("Login Success");
//    }

    private void addRefreshEntity(String memberId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setMemberId(memberId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
