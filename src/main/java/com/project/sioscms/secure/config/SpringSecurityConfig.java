package com.project.sioscms.secure.config;

import com.project.sioscms.secure.handler.LoginFailureHandler;
import com.project.sioscms.secure.handler.LoginSuccessHandler;
import com.project.sioscms.secure.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()  //cors 인증 비활성화
                .authorizeHttpRequests(request -> request
//                        .antMatchers("/", "/main", "/static/**", "/login/**", "/**/member/join/**", "/cms/auth/**").permitAll() //권한 적용 예외 경로 설정
//                        .anyRequest().authenticated()
                                .antMatchers("/cms/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                ).formLogin(login -> login
                                .loginPage("/cms/auth/login")    //로그인 페이 설정
                                .loginProcessingUrl("/cms/auth/login-process")   //로그인 프로세스 페이지 설정
                                .usernameParameter("userId")    //로그인 유저 id 파라미터명 설정(default : username)
                                .passwordParameter("userPw")    //로그인 유저 pw 파라미터 설정(default : password)
                                .successHandler(loginSuccessHandler)
                                .failureHandler(loginFailureHandler)
//                                .defaultSuccessUrl("/cms/main", true) //로그인 성공 시 이동 페이지 설정
//                                .failureForwardUrl("")    //로그인 실패 시 이동 페이지 설정
                                .permitAll()    //성공 이동 페이지 권한 예외처리
                ).logout(logout -> logout
                        .logoutSuccessUrl("/")  //로그이웃시 이동할 페이지
                        .invalidateHttpSession(true));    //로그아웃 기본설(/logout 호출시 로그아웃처리)

        http.headers().frameOptions().sameOrigin(); //Iframe 접근 가능(스마트 에디터)

        http.headers().xssProtection(); //Xss 방지 옵션
//        http.headers().xssProtection().and().contentSecurityPolicy("script-src 'self'"); //Xss 방지 옵션

        return http.build();
    }

    //커스텀 authenticationProvider 설정
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }
}
