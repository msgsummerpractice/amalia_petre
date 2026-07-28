package com.example.spring_data_jpa.configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize)->{
                    authorize.requestMatchers("/login","/perform_login").permitAll()
                    .requestMatchers("/users","/users/**").hasAnyRole("ADMIN","USER");
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults())
                .formLogin(form->form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/users")
                    .failureUrl("/login?error=true")
                    .permitAll()
                );
        return http.build();
    } 

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails john = User.builder()
    //             .username("john")
    //             .password(passwordEncoder().encode("12345"))
    //             .roles("USER")
    //             .build();

    //     UserDetails sam = User.builder()
    //             .username("sam")
    //             .password(passwordEncoder().encode("12345"))
    //             .roles("ADMIN")
    //             .build();

    //     return new InMemoryUserDetailsManager(john,sam);
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
