package org.spring.study.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(HttpBasicConfigurer::disable) // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize // 리퀘스트에 대한 사용권한 체크
                        .requestMatchers("/sign-api/sign-in/**", "/sign-api/sign-up/**",
                                "/sign-api/exception").permitAll() // 가입 및 로그인 주소는 허용
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll() //product로 시작하는 Get 요청은 허용
                        .requestMatchers("**exception**").permitAll()
                        .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능)
                )
                .exceptionHandling((exception) -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(
                "/v3/api-docs/**", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception", "/swagger-ui/**"
        );
    }
}
