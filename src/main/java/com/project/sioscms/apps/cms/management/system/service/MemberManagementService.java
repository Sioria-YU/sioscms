package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final AccountRepository accountRepository;

    public List<Account> getAdminList(){
        return accountRepository.findAllByIsDeleteAndRole(false, Account.Role_Type.ADMIN);
    }
}
