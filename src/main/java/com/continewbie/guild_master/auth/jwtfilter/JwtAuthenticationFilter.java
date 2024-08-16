package com.continewbie.guild_master.auth.jwtfilter;

import com.continewbie.guild_master.auth.jwt.JwtTokenizer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 로그인 요청 시 가장 먼저 만나는 UsernamePasswordAuthenticationFilter를 상속받아 토큰기반 인증을 위한 커스텀 필터 생성
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenizer jwtTokenizer;

    // 인증을 위한 1차 Authentication (UserNamePasswordAuthenticationToken) 객체를 관리해줄 AuthenticationManager
    // 로그인 할 때 토큰을 발행 해줄 JwtTokenizer 생성자를 통해 의존성 주입

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }


}
