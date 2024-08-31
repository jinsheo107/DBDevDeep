package com.dbdevdeep.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // 모든 요청에 대해 인증이 필요하도록 설정
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
            )
            // 로그인 설정
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("emp_id")
                .passwordParameter("emp_pw")
                .permitAll()  // 로그인 페이지와 로그인 처리 URL에 대한 접근을 허용
                .successHandler(new MyLoginSuccessHandler())
                .failureHandler(new MyLoginFailureHandler())
            )
            // 로그아웃 설정
            .logout(logout -> 
                logout.permitAll()
            );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
        		.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
