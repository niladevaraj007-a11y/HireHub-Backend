package com.Hirehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Disable CSRF because this backend is used by React
            .csrf(csrf -> csrf.disable())

            // Enable CORS using the separate CorsConfig
            .cors(cors -> {
            })

            // REST API - no HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // -------------------------------------------------
                // CORS preflight
                // -------------------------------------------------
                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                ).permitAll()

                // -------------------------------------------------
                // AUTHENTICATION
                // -------------------------------------------------
                .requestMatchers(
                    "/api/auth/**"
                ).permitAll()

                // -------------------------------------------------
                // PUBLIC JOB APIs
                // -------------------------------------------------
                .requestMatchers(
                    "/api/jobs/**"
                ).permitAll()

                // -------------------------------------------------
                // APPLICATION APIs
                //
                // Currently your frontend does not have JWT
                // authentication configured, so allow these.
                // -------------------------------------------------
                .requestMatchers(
                    "/api/applications/**"
                ).permitAll()

                // -------------------------------------------------
                // NOTIFICATION APIs
                // -------------------------------------------------
                .requestMatchers(
                    "/api/notifications/**"
                ).permitAll()

                // -------------------------------------------------
                // INTERVIEW APIs
                // -------------------------------------------------
                .requestMatchers(
                    "/api/interviews/**"
                ).permitAll()

                // -------------------------------------------------
                // Everything else
                // -------------------------------------------------
                .anyRequest().permitAll()
            );

        return http.build();
    }
}