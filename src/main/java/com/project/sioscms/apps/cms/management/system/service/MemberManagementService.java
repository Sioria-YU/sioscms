package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final AccountRepository accountRepository;

    public List<AccountDto.Response> getAdminList(){

        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();

        restriction.equals("isDelete", false);
        restriction.equals("role", Account.Role_Type.ADMIN);

        return accountRepository.findAll(restriction.toSpecification()).stream().map(Account::toResponse).collect(Collectors.toList());

//        return accountRepository.findAllByIsDeleteAndRole(false, Account.Role_Type.ADMIN);
    }

}
