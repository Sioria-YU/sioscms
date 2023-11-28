package com.project.sioscms.apps.account.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.apps.account.mapper.AccountMapper;
import com.project.sioscms.secure.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService extends EgovAbstractServiceImpl {

    public final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 회원 단일 조회
     * @param id
     * @return
     */
    public AccountDto.Response findUser(long id){

        Account user = (Account) accountRepository.findById(id).orElseThrow(NullPointerException::new);

        return user.toResponse();
    }

    public Optional<Account> findByUserId(String userId){
        return accountRepository.findByUserId(userId);
    }

    /**
     * 회원 등록
     * @param dto
     * @return
     */
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
            account.setCreatedBy(account);
            account.setUpdatedBy(account);
            account.setState("T");
            account.setLoginFailedCount(0L);
            account.setIsLocked(false);
            account.setLockedDateTime(null);
            return account;
        }else{
            log.error("회원가입 데이터 오류 발생!!!");
            return null;
        }
    }

    /**
     * 로그인 아이디 중복 체크
     * @param userId
     * @return
     */
    public Boolean userIdDuplicationCheck(String userId){
        return accountRepository.countAccountByUserId(userId) <= 0;
    }

    /**
     * 회원 삭제처리
     * @param idList
     * @return
     */
    @Transactional
    public Boolean daleteUsers(List<Long> idList){
        try {
            UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            accountRepository.updateAllByIds(idList, LocalDateTime.now(), userAccount.getAccount().getId());
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Transactional
    public Boolean passwordChange(String userId, String userPwd){
        if(userId != null && userPwd != null){
            Account account = accountRepository.findByUserId(userId).orElse(null);
            if(account == null){
                return false;
            }else{
                account.setUserPassword(passwordEncoder.encode(userPwd));
                return true;
            }
        }else {
            return false;
        }
    }

    @Transactional
    public void updateLoginSuccess(Account account){
        account.setLoginFailedCount(0L);
        account.setIsLocked(false);
        account.setLockedDateTime(null);
        accountRepository.save(account);
        accountRepository.flush();
    }

    @Transactional
    public void updateLoginFailed(Account account, long failedCnt, boolean isLocked){
        account.setLoginFailedCount(failedCnt);
        account.setIsLocked(isLocked);
        if(isLocked && account.getLockedDateTime() == null){
            account.setLockedDateTime(LocalDateTime.now());
        }
        accountRepository.save(account);
        accountRepository.flush();
    }

}
