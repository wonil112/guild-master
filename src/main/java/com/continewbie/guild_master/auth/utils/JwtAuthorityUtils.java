package com.continewbie.guild_master.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthorityUtils {
    //import 주의 :org.springframework.beans.factory.annotation.Value
    @Value("${mail.address.admin}")
    private String adminEmail;

    //자바 시큐리티에서 관리할 인가정보 - "ROLE_" + "권한이름"
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils
            .createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

    //DB에 저장할 때 관리할 인가정보
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");

    private final List<String> USER_ROLES_STRING = List.of("USER");


}
