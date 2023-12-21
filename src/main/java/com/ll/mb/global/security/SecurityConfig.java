package com.ll.mb.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableMethodSecurity    // 메소드 수준의 보안 (@PreAuthorize 등) 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .anyRequest()
                                .permitAll()     // 모든 HTTP 요청에 대해 접근 허용
                )
                .headers(     // HTTP 헤더 보안 설정
                        headers -> headers
                                .addHeaderWriter(
                                        new XFrameOptionsHeaderWriter(     // Clickjacking Attack 방지
                                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)  //웹 페이지와 프레임이 같은 프로토콜, 도메인 이름, 포트를 사용해야 한다.
                                )
                )
                .csrf(
                        csrf -> csrf
                                .ignoringRequestMatchers(
                                        "/h2-console/**"    // 해당 경로의 요청에는 CSRF 보호 비활성화
                                )
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


