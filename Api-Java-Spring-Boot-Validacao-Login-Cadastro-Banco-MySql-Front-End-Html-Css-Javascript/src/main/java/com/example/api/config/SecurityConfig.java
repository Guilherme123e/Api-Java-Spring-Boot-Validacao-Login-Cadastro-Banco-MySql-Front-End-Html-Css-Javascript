package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // desabilita CSRF para facilitar
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/login.html",
                "/register.html",
                "/login-sucess.html",
                "/register-sucess.html",
                "/js/**",
                "/css/**",
                "/images/**",
                "/api/login",
                "/api/cadastro")
            .permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form.disable());

    return http.build();
  }
}