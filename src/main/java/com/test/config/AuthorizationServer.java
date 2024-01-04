package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthorizationServer {

    private final AuthFilter authFilter;

    private final AuthenticationProvider authenticationProvider;

    public AuthorizationServer(AuthFilter authFilter, AuthenticationProvider authenticationProvider) {
            this.authFilter = authFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    // Authorization
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.GET, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/kapal").permitAll()

                        .requestMatchers(HttpMethod.POST, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.GET, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/divisi").permitAll()

                        .requestMatchers(HttpMethod.POST, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.GET, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/cabang").authenticated()

                        .requestMatchers(HttpMethod.POST, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.GET, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/biodata").authenticated()

                        .requestMatchers(HttpMethod.POST, "/absensi").authenticated()
                        .requestMatchers(HttpMethod.GET, "/absensi").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/absensi").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/absensi").authenticated()

                )



                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}