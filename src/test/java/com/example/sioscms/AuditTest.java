package com.example.sioscms;

import com.project.sioscms.SioscmsApplication;
import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes= SioscmsApplication.class)
@Transactional
public class AuditTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void auditTest(){
        AccountDto.Request dto = new AccountDto.Request();
        dto.setName("관리자");
        dto.setUserId("admin2");
        dto.setState("T");
        dto.setIsDelete(false);
        dto.setRole(Account.Role_Type.ADMIN);
        dto.setUserPassword("1234");

        accountService.saveUser(dto);

        Account account = accountService.findByUserId("admin").orElseThrow(NullPointerException::new);

        System.out.println("account ::::::::::::::::: ");
        System.out.println(account.getCreatedOn() + " //// " + account.getUpdatedOn() );
    }
}
