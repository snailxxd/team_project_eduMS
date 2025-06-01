package com.infoa.educationms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /*
                .authorizeHttpRequests(auth -> auth
                        // 允许所有静态资源访问
                        .requestMatchers("/index.html", "/assets/**", "/favicon.ico").permitAll()
                        // 允许所有前端路由路径
                        .requestMatchers("/information-manage", "/course-manage", "/grade-query",
                                "/grade-modify", "/grade-analyze").permitAll()
                        // API需要认证
                        .requestMatchers("/api/**").authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/information-manage", true) // 登录成功后强制跳转到根路径
                        .permitAll()
                )*/
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll() // 允许所有请求
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}