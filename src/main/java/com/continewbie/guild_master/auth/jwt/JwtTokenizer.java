package com.continewbie.guild_master.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

// 스프링 Bean 등록
@Component
public class JwtTokenizer {

    //  import 주의 Value - 스프링 빈팩토리에서 관리하는 Value
    //  해당 경로에 있는 키를 시크릿키로 사용 현재 로컬에서 ${JWT_SECRET_KEY} 경로에 환경변수로 설정 해뒀음
    @Getter
    @Value("${jwt.key}")
    private String secretKey;

    //  yml 해당 경로에 설정해둔 엑세스 토큰 만료기한
    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpiration;

    //  yml 해당 경로에 설정해둔 엑세스 토큰 만료기한
    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpiration;

    // secretKey 를 base64 형태로 인코딩
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // base64로 인코딩된 secretKey를 byte[] 형식으로 디코딩하고 해시함수를 적용하여
    // 토큰 생성과 검증에 사용할 시그니처에서 사용할 Key 객체로 변환하는 데에 사용
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }


    // AccessToken 발급 메서드
    // 매개 변수로 페이로드의 포함되는 사용자의 정보를 담고있는 claims(사용자Id,권한,이름 등 크리덴셜 x ), 키 벨류 형태로 들어오기 때문에 Map
    // subject 토큰의 주체로 어떤 사용자에 의해 발행 되었는지 식별하는데 사용 현재 사용자 식별은 (email)로 함.

    public String generateAccessToken(Map<String, Object> claims, String subject,
                                      Date expiration, String base64EncodedSecretKey) {

        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject,
                                       Date expiration, String base64EncodedSecretKey) {

        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // 매개변수 jws  = 인증된 claim을 포함하는 JWT 문자열 임.
    // 시그니처 검증에 사용할 인코딩된 시크릿 키
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                //JWT 시그니처를 검증하는데 사용 되는 키 설정
                .setSigningKey(key)
                .build()
                // JWT를 파싱해서 JWT에 포함된 클레임을 추출
                .parseClaimsJws(jws);
        // 파싱된 JWT에서 클레임을 포함하는 JWS<Claims> 객체를 반환하며
        // 페이로드(claim)을 포함하며 서명 검증 결과도 포함된다.
        return claimsJws;
    }

    // 시그니처 검증을 위한 메서드
    // 1. API 요청 검증 : 클라이언트가 서버에 API를 요청할 때 신뢰할 수 있는지 확인
    // 2. 로그인 및 인증  : 사용자가 로그인해서 발급받은 JWT로 이후 요청에서 JWT 서명을 검증하여 사용자 인증 상태 확인
    // 3. 토큰 검증 " 인증과 권한을 부여하는 로직에서 활용
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // 토큰의 유효기간 확인 메서드
    public Date getTokenExpiration(int expiration) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiration);
        return calendar.getTime();
    }

}
