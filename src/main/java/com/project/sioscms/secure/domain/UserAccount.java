package com.project.sioscms.secure.domain;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account) {
        super(account.getUserId(), account.getUserPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name())));
        this.account = account;
    }

    public AccountDto.Response getAccountDto(){
        if(this.account == null){
            return null;
        }else{
            return this.account.toResponse();
        }
    }
}
