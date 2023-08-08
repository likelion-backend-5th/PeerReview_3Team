package com.likelion.MutsaSNS.config;

import com.likelion.MutsaSNS.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean  // 메소드의 결과를 Bean 객체로 등록해주는 어노테이션
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF: Cross Site Request Forgery
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers(
                                "/no-auth",
                                "/token/issue",
                                "/users/login"
                        )
                        .permitAll()
                        .requestMatchers(
                                "/",
                                "/users/register"
                        )
                        .anonymous()
                        .anyRequest()
                        .authenticated()
                )
//                .formLogin(
//                        formLogin -> formLogin
//                                .loginPage("/users/login")
//                                .defaultSuccessUrl("/users/my-profile")
//                                .failureUrl("/users/login?fail")
//                                .permitAll()
//                )
//                .logout(
//                        logout -> logout
//                                .logoutUrl("/users/logout")
//                                .logoutSuccessUrl("/users/login")
//                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtTokenFilter,
                        AuthorizationFilter.class
                );
        return http.build();
    }

//    public UserDetailsManager userDetailsManager(
//            PasswordEncoder passwordEncoder
//    ) {
//        // 임시 User
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder.encode("password"))
//                .build();
//        // Spring 에서 미리 만들어놓은 사용자 인증 서비스
//        return new InMemoryUserDetailsManager(user1);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}