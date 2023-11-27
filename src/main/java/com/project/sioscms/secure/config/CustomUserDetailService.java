package com.project.sioscms.secure.config;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = accountService.findByUserId(userId).orElseThrow(() -> (new UsernameNotFoundException("존재하지 않는 회원입니다.")));

        //security User객체 커스터마이징 적용하여 주석처리
        /*return User.builder()
                .username(account.getUserId())
                .password(account.getUserPassword())
                .roles(account.getRole().name())
                .build();*/
        return new UserAccount(account);
    }
}
