package com.project.sioscms.common.utils;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.secure.domain.UserAccount;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommonUserAuditorAware implements AuditorAware<Account> {

    @Override
    public Optional<Account> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }else if ("anonymousUser".equals(authentication.getPrincipal())){
            return Optional.empty();
        }else {
            UserAccount userAccount = (UserAccount) authentication.getPrincipal();

            return Optional.of(userAccount.getAccount());
        }
    }
}
