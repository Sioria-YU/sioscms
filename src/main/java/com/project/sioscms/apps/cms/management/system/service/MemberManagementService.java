package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.apps.cms.management.system.domain.dto.RequestDto;
import com.project.sioscms.common.utils.jpa.page.PaginationUtil;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final AccountRepository accountRepository;

    /**
     * 관리자 목록 조회
     * @param requestDto: userId, name, gender
     * @return
     */
    public List<AccountDto.Response> getAdminList(RequestDto requestDto){

        //List 동적쿼리 조건생성
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();  //기본 값 AND 조건으로 적용됨.
        restriction.equals("isDelete", false);
        restriction.equals("role", Account.Role_Type.ADMIN);

        //검색 조건 추가
        if(requestDto != null){
            if(requestDto.getUserId() != null && !requestDto.getUserId().isEmpty()){
                restriction.equals("userId", requestDto.getUserId());
            }
            if(requestDto.getName() != null && !requestDto.getName().isEmpty()){
                restriction.equals("name", requestDto.getName());
            }
            if(requestDto.getGender() != null && !requestDto.getGender().isEmpty()){
                restriction.equals("gender", requestDto.getGender());
            }
        }

        return accountRepository.findAll(restriction.toSpecification()).stream().map(Account::toResponse).collect(Collectors.toList());
    }

    /**
     * 사용자 목록 조회
     * @param requestDto : userId, name, gender
     * @return
     */
    public SiosPage getUserList(RequestDto requestDto){
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

//        Page<Account> accountPage = accountRepository.findAll(restriction.toSpecification(), requestDto.toPageableWithSortedByCreatedOn(Sort.Direction.DESC));
//        return accountRepository.findAll(restriction.toSpecification()).stream().map(Account::toResponse).collect(Collectors.toList());
        return PaginationUtil.of(accountRepository.findAll(restriction.toSpecification(), requestDto.toPageableWithSortedByCreatedOn(Sort.Direction.DESC)).map(Account::toResponse));
    }

}
