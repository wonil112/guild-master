package com.continewbie.guild_master.config;

import com.continewbie.guild_master.auth.filter.JwtAuthenticationFilter;
import com.continewbie.guild_master.auth.filter.JwtVerificationFilter;
import com.continewbie.guild_master.auth.handler.MemberAccessDeniedHandler;
import com.continewbie.guild_master.auth.handler.MemberAuthenticationEntryPoint;
import com.continewbie.guild_master.auth.handler.MemberAuthenticationFailureHandler;
import com.continewbie.guild_master.auth.handler.MemberAuthenticationSuccessHandler;
import com.continewbie.guild_master.auth.jwt.JwtTokenizer;
import com.continewbie.guild_master.auth.utils.JwtAuthorityUtils;
import com.continewbie.guild_master.member.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final JwtAuthorityUtils jwtAuthorityUtils;
    private final RedisTemplate redisTemplate;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, JwtAuthorityUtils jwtAuthorityUtils, RedisTemplate redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.jwtAuthorityUtils = jwtAuthorityUtils;
        this.redisTemplate = redisTemplate;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // h2 웹 콘솔 화면 자체가 내부적으로 <frame> 을 사용하고 있음. 이를 정상적으로 수행하도록 함.
        // 동일 출처로부터 들어오는 요청만 페이지 렌더링을 허용.
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint( new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST,"/members").permitAll()
                        .antMatchers(HttpMethod.POST,"/members/**").permitAll()
                        .antMatchers(HttpMethod.PATCH,"/members").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH,"/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/members").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/members").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/events").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/events/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH,"/events").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH,"/events/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/events").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/events/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/events").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/events/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/guilds").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/guilds/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/guilds").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/guilds/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/guilds").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/guilds/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/guilds").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/guilds/**").hasRole("USER")
                        .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://guild-master.s3-website.ap-northeast-2.amazonaws.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
//        POST 요청일 때 헤더에 해당 키 사용 가능 리스폰스에 노출을 안시키는걸 임의로 노출 가능하게 설정
        configuration.setExposedHeaders(Arrays.asList("Authorization","memberId"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure (HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisTemplate);
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, jwtAuthorityUtils, redisTemplate);


            builder.addFilter(jwtAuthenticationFilter)
                    // Authentication 다음에 Verification 필터를 실행해라
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);

        }
    }

}

