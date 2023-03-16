package com.project.sioscms.apps.account.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/api/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService userService;

//    @ApiOperation(value = "사용자 조회", notes = "사용자 조회 api")
    @GetMapping(value="/{id}")
    public ResponseEntity<AccountDto.Response> getUsers(@PathVariable long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping(value = "/id-check/{userId}")
    public ResponseEntity<Boolean> userIdDuplicationCheck(@PathVariable String userId){
        return ResponseEntity.ok(userService.userIdDuplicationCheck(userId));
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<Boolean> userDelete(HttpServletRequest request, @RequestParam(value = "idList[]", defaultValue = "") List<Long> idList){
        return ResponseEntity.ok(userService.daleteUsers(idList));
    }

//    @ApiOperation(value = "사용자 정보 아이디로 조회", notes = "사용자 조회 api")
    /*@GetMapping(value="/userId/{userId}")
    public ResponseEntity<AccountDto.Response> getUserInfo(@PathVariable String userId){
        return ResponseEntity.ok(userService.findUserByUserId(userId));
    }*/

//    @ApiOperation(value = "사용자 성별로 조회", notes = "사용자 조회 api")
    /*@GetMapping(value="/gender/")
    public ResponseEntity<List<AccountDto.Response>> getUserInfo(AccountDto.Request dto){
        return ResponseEntity.ok(userService.getUserByGenderAndIsDelete(dto));
    }*/

}
