package com.example.social_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for H2 console
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                                .anyRequest().permitAll()
                )
                .headers(headers -> headers.disable()) // Fully disable security headers for H2 console
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
