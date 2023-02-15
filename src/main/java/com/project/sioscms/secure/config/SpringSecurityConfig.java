package com.project.sioscms.secure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()  //cors 인증 비활성화
        .authorizeHttpRequests(request -> request
                .antMatchers("/","/main","/static/**","/login/**","/join/**").permitAll() //권한 적용 예외 경로 설정
                .anyRequest().authenticated()
        ).formLogin(login -> login
//                        .loginPage("/login")    //로그인 페이 설정
//                        .loginProcessingUrl("/login-proc")   //로그인 프로세스 페이지 설정
//                        .usernameParameter("userId")    //로그인 유저 id 파라미터명 설정(default : username)
//                        .passwordParameter("userPw")    //로그인 유저 pw 파라미터 설정(default : password)
                .defaultSuccessUrl("/main", true) //로그인 성공 시 이동 페이지 설정
                .permitAll()    //성공 이동 페이지 권한 예외처리
        ).logout(Customizer.withDefaults());    //로그아웃 기본설(/logout 호출시 로그아웃처리)

        return http.build();
    }
}
