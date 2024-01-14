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

                        //CLASS KAPAL
                        .requestMatchers(HttpMethod.POST, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.GET, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/kapal").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/kapal").authenticated()

                        //CLASS DIVISI
                        .requestMatchers(HttpMethod.POST, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.GET, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/divisi").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/divisi").permitAll()

                        //CLASS CABANG
                        .requestMatchers(HttpMethod.POST, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.GET, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/cabang").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/cabang").authenticated()

                        //CLASS BIODATA
                        .requestMatchers(HttpMethod.POST, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.GET, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.GET, "/biodata/bla").authenticated()
                        .requestMatchers(HttpMethod.GET, "/biodata/count").authenticated()
                        .requestMatchers(HttpMethod.GET, "/biodata/countType").authenticated()
                        .requestMatchers(HttpMethod.GET,"/biodata/countAll").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/biodata").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/biodata").authenticated()

                        //CLASS ABSENSI
                        .requestMatchers(HttpMethod.POST, "/absensi/checkin").authenticated()
                        .requestMatchers(HttpMethod.POST,"/absensi/checkout").authenticated()
                             //   .requestMatchers(HttpMethod.GET, "/absensi/barChart").authenticated()
                        .requestMatchers(HttpMethod.GET, "/absensi").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/absensi").authenticated()

                )



                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}