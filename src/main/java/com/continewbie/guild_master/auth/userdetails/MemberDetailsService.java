package com.continewbie.guild_master.auth.userdetails;

import com.continewbie.guild_master.auth.utils.JwtAuthorityUtils;
import com.continewbie.guild_master.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

    //  DB에서 크리덴셜을 조회, 비교하여 인가정보가 들어있는 UserDetails 를 생성하고 AuthenticationProvider에 전달
    //  인증된 2차 Authentication (Principal, 암호화된 Credentials , 인가정보를 담은 (Collection<GrantedAuthority>) 객체)
    //  생성하기 위해 사용할 커스텀 UserDetailsService 구현
@Component
public class MemberDetailsService implements UserDetailsService {

    //  DB에서의 조회를 위해 MemberRepository, 현재 토큰을 통해 사용자의 권한 및 환경변수로 들어오는 관리자의 정보를 관리할 것이기에
    //  유틸리티 클래스 JwtAuthorityUtils 생성자 의존성 주입
    private final MemberRepository memberRepository;
    private final JwtAuthorityUtils jwtAuthorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, JwtAuthorityUtils jwtAuthorityUtils) {
        this.memberRepository = memberRepository;
        this.jwtAuthorityUtils = jwtAuthorityUtils;
    }

    //    UserDetailsService 의 loadUserByUsername 재정의 필수
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
