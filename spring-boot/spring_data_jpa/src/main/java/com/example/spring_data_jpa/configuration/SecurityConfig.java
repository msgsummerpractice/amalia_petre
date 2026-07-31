package com.example.spring_data_jpa.configuration;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

import org.springframework.security.authentication.AuthenticationManager;
import com.example.spring_data_jpa.service.CustomOneTimeTokenService;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.core.authority.FactorGrantedAuthority;

@EnableMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.PASSWORD_AUTHORITY, FactorGrantedAuthority.OTT_AUTHORITY
})
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AdminMfaAuthorizationManager admin;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/login", "/ott/generate").permitAll()
                        .requestMatchers("/api/users/admin/**").access(admin)
                        .requestMatchers("/api/users/user/**").hasRole("ROLE_USER")
                        .anyRequest().authenticated()
                )
                .oneTimeTokenLogin(ott -> ott
                        .tokenGenerationSuccessHandler(oneTimeTokenGenerationSuccessHandler())
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public OneTimeTokenService oneTimeTokenService() {
        CustomOneTimeTokenService service = new CustomOneTimeTokenService();
        service.setTokenExpiresIn(Duration.ofMinutes(3));
        return service;
    }

    @Bean
    public OneTimeTokenGenerationSuccessHandler oneTimeTokenGenerationSuccessHandler() {
        return (request, response, oneTimeToken) -> {
            System.out.println("==========================================");
            System.out.println("GENERATED OTT PIN: " + oneTimeToken.getTokenValue());
            System.out.println("==========================================");

            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Token generated successfully! Check logs for PIN.\"}");
        };
    }
}