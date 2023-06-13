package com.example.todo.config;

import com.example.todo.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 기본적으로 필터로 외부 접속을 제한하고 있는데 그것을 해체해준다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                // 세션 인증을 사용하지 않겠다는 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 어떤 요청에서 인증을 안 할 것인지, 언제 할 것인지 설정
                .authorizeRequests().antMatchers("/", "/api/auth/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/todos").hasRole("ADMIN")
                .anyRequest().authenticated()
                ;

        // 토큰인증 필터 연결
        http.addFilterAfter(
                jwtAuthFilter
                , CorsFilter.class // import 주의 : Spring 꺼
        );

        return http.build();
    }



}
