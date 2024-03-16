package com.oop.web1.configs;
import com.oop.web1.routes.RoutesMovie;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
//import static com.oop.web1.models.RoleModel.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/api/user/register",
                                "/",
                                "/api/user/login",
                                "/api/user/change/{id}",
                                "/api/user/userDetail/{id}",
                                "/api/user/logout",
                                "/api/admin/listUser",
                                "/api/admin/deleteUsers",
                                "/api/manager/listUser",
                                "/api/manager/deleteUsers",
                                "/api/movies",
                                "/movies/upload",
                               // "/websocket",
                                "/chat",
                                "/api/home").permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }
}