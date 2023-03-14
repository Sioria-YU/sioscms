package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.apps.account.mapper.AccountMapper;
import com.project.sioscms.apps.cms.management.system.domain.dto.MemberSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //region ADMIN
    /**
     * 관리자 목록 조회
     * @param requestDto: userId, name, gender
     * @return
     */
    public SiosPage<AccountDto.Response> getAdminList(MemberSearchDto requestDto) throws Exception {

        //List 동적쿼리 조건생성
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();  //기본 값 AND 조건으로 적용됨.
        restriction.equals("isDelete", false);
        restriction.equals("role", Account.Role_Type.ADMIN);

        //검색 조건 추가
        if(requestDto.getUserId() != null && !requestDto.getUserId().isEmpty()){
            restriction.equals("userId", requestDto.getUserId());
        }
        if(requestDto.getName() != null && !requestDto.getName().isEmpty()){
            restriction.equals("name", requestDto.getName());
        }
        if(requestDto.getGender() != null && !requestDto.getGender().isEmpty()){
            restriction.equals("gender", requestDto.getGender());
        }

        return new SiosPage<>(accountRepository.findAll(restriction.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Account::toResponse), requestDto.getPageSize());
    }

    /**
     * 관리자 등록
     * @param dto :AccountDto.Request
     * @return AccountDto.Response
     */
    public AccountDto.Response saveAdmin(AccountDto.Request dto){
        if(accountRepository.countAccountByUserId(dto.getUserId()) > 0){
            log.error("중복 아이디 발생!!!");
            return null;
        }

        if(dto.getUserPassword() != null){
            dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        }

        dto.setState("T");
        Account account = AccountMapper.mapper.toEntity(dto);

        if(account != null){
            accountRepository.save(account);
            return account.toResponse();
        }else{
            log.error("회원가입 데이터 오류 발생!!!");
            return null;
        }
    }
    //endregion

    //region USER
    /**
     * 사용자 목록 조회
     * @param requestDto : userId, name, gender
     * @return
     */
    public SiosPage<AccountDto.Response> getUserList(MemberSearchDto requestDto) throws Exception{
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();
        restriction.equals("isDelete", false);
        restriction.equals("role", Account.Role_Type.USER);

        //검색 조건 추가
        if(requestDto.getUserId() != null && !requestDto.getUserId().isEmpty()){
            restriction.equals("userId", requestDto.getUserId());
        }
        if(requestDto.getName() != null && !requestDto.getName().isEmpty()){
            restriction.equals("name", requestDto.getName());
        }
        if(requestDto.getGender() != null && !requestDto.getGender().isEmpty()){
            restriction.equals("gender", requestDto.getGender());
        }

        return new SiosPage<>(accountRepository.findAll(restriction.toSpecification(), requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Account::toResponse));
    }
    //endregion
}
