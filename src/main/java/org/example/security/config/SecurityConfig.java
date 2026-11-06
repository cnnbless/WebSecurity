package org.example.security.config;

/*
  @author   Tony
  @project   security
  @class  SecurityConfig
  @version  1.0.0 
  @since 13.10.2026 - 19.45
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Endpoints accessible by USER, ADMIN, SUPERADMIN
                        .requestMatchers(HttpMethod.GET, "/api/v1/vehicles/**", "/api/v1/fleets/**")
                        .hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/vehicles/*/rent")
                        .hasAnyRole("USER", "ADMIN", "SUPERADMIN")

                        // Endpoints accessible by ADMIN and SUPERADMIN
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**")
                        .hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/vehicles/**", "/api/v1/fleets/**")
                        .hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/vehicles/**", "/api/v1/fleets/**")
                        .hasAnyRole("ADMIN", "SUPERADMIN")

                        // Endpoints accessible ONLY by SUPERADMIN
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**")
                        .hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**")
                        .hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**")
                        .hasRole("SUPERADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails superadmin = User.builder()
                .username("superadmin")
                .password(passwordEncoder.encode("superadmin123"))
                .roles("SUPERADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin, superadmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
