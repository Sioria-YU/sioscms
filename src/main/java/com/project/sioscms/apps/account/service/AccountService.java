package com.project.sioscms.apps.account.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;

    public ResponseEntity<AccountDto.Response> getAccount(long id){

        Account user = (Account) accountRepository.findById(id).orElseThrow(NullPointerException::new);

        return ResponseEntity.ok(user.toResponse());
    }

}
