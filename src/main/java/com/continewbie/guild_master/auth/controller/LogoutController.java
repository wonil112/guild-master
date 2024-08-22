package com.continewbie.guild_master.auth.controller;

import com.continewbie.guild_master.auth.service.TokenBlacklistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/members")
public class LogoutController {

    private final TokenBlacklistService tokenBlacklistService;

    public LogoutController(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        System.out.println("Logout endpoint hit"); // 메서드 실행 확인
        String token = request.getHeader("Authorization");
        System.out.println("Received token: " + token); // 받은 토큰 출력
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenBlacklistService.addToBlacklist(token);
            System.out.println("Token added to blacklist: " + token);
        } else {
            System.out.println("No valid token received");
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}