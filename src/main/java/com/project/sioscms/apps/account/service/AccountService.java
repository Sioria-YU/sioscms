package com.project.sioscms.apps.account.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.apps.account.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountDto.Response findUser(long id){

        Account user = (Account) accountRepository.findById(id).orElseThrow(NullPointerException::new);

        return user.toResponse();
    }

    public Optional<Account> findByUserId(String userId){
        return accountRepository.findByUserId(userId);
    }

    @Transactional
    public Account saveUser(AccountDto.Request dto){

        if(dto.getUserPassword() != null){
            dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        }

        Account account = AccountMapper.mapper.toEntity(dto);

        if(account != null){
            accountRepository.save(account);
            accountRepository.flush();

            //회원 가입 기본 시퀀스 세팅
            account.setCreatedBy(account.getId());
            account.setUpdatedBy(account.getId());
            account.setState("T");
            return account;
        }else{
            log.error("회원가입 데이터 오류 발생!!!");
            return null;
        }
    }

    public Boolean userIdDuplicationCheck(String userId){
        return accountRepository.countAccountByUserId(userId) <= 0;
    }

}
