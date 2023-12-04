package com.project.sioscms.secure.provider;

import com.project.sioscms.secure.config.CustomUserDetailService;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //로그인 인가검증
        UserAccount userAccount = (UserAccount) userDetailService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userAccount.getAccount().getUserPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }else if(userAccount.getAccount().getIsLocked() && LocalDateTime.now().minusMinutes(30).isBefore(userAccount.getAccount().getLockedDateTime())){
            throw new DisabledException("DisabledException");
        }

        return new UsernamePasswordAuthenticationToken(
                userAccount,
                null,
                userAccount.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
