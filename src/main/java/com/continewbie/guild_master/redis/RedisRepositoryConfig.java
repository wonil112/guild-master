package com.continewbie.guild_master.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories // Redis 레퍼지토리를 활성화하여 데이터를 JPA처럼 다룰 수 있게 해주는 애너테이션
public class RedisRepositoryConfig {
    //   properties 에서 호스트 주소를 가져오는 경로
    @Value("${spring.data.redis.host}") // 스프링 빈팩토리에서 관리하는 벨류 import 주의
    private String host;
    //   properties 에서 포트 주소를 가져오는 경로
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration 객체를 생성
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        Redis 서버 호스트 설정
        redisStandaloneConfiguration.setHostName(host);
//        Redis 서버 포트 설정
        redisStandaloneConfiguration.setPort(port);
//        LettuceConnectionFactory를 사용하여 Redis 연결을 설정
//        LettuceConnectionFactory를 연결 풀을 지원하고, Redis 명령어를 비동기적으로 처리.
//        팩토리 안에 설정 정보를 저장해줌
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceConnectionFactory;

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // Redis의 키와 값을 직렬화하는 방식을 설정
        // 여기서는 StringRedisSerializer를 사용하여 키와 값을 문자열로 직렬화합니다.
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 이 설정은 Redis에 데이터를 저장할 때 직렬화 방식을 지정하는 것으로, 데이터 저장 형식을 정의합니
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate; //RedisTemplate 빈 반환
    }
}

