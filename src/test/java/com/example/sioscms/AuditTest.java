package com.example.sioscms;

import com.project.sioscms.SioscmsApplication;
import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.domain.entity.Account;
import com.project.sioscms.apps.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@SpringBootTest(classes= SioscmsApplication.class)  //전체 bean을 탐색하여 메모리에 로드함 -> 단위테스트 빠르게 할 경우 Mock을 사용할 것
@Transactional
@Rollback(value = true) //테스트 후 데이터 롤백 설정(true:롤백 / false:롤백하지 않음)
public class AuditTest {

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("SpringData Auditing(작성자,수정자,작성일,수정일 자동입력) 테스트")
    public void auditTest(){
        /*AccountDto.Request dto = new AccountDto.Request();
        dto.setName("관리자");
        dto.setUserId("admin2");
        dto.setState("T");
        dto.setIsDelete(false);
        dto.setRole(Account.Role_Type.ADMIN);
        dto.setUserPassword("1234");

        accountService.saveUser(dto);

        Account account = accountService.findByUserId("admin").orElseThrow(NullPointerException::new);
        System.out.println("CreatedOn :::: " + account.getCreatedDateTime() + " ||| " + "UpdatedOn :::: " + account.getUpdatedDateTime() );

        Assert.notNull(account.getCreatedDateTime(), "CreatedOn is null");
        Assert.notNull(account.getUpdatedDateTime(), "UpdatedOn is null");*/
    }
}
