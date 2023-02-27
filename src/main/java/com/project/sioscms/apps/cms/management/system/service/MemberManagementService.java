package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.apps.cms.management.system.domain.dto.RequestDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final AccountRepository accountRepository;

    /**
     * 관리자 목록 조회
     * @param requestDto: userId, name, gender
     * @return
     */
    public SiosPage<AccountDto.Response> getAdminList(RequestDto requestDto) throws Exception {

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

        return new SiosPage<>(accountRepository.findAll(restriction.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Account::toResponse));
    }

    /**
     * 사용자 목록 조회
     * @param requestDto : userId, name, gender
     * @return
     */
    public SiosPage<AccountDto.Response> getUserList(RequestDto requestDto) throws Exception{
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

}
