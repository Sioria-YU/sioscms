package com.project.sioscms.apps.account.controller;

import com.project.sioscms.apps.account.domain.dto.AccountDto;
import com.project.sioscms.apps.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService userService;

//    @ApiOperation(value = "사용자 조회", notes = "사용자 조회 api")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto.Response> getUsers(@PathVariable long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("/id-check/{userId}")
    public ResponseEntity<Boolean> userIdDuplicationCheck(@PathVariable String userId){
        return ResponseEntity.ok(userService.userIdDuplicationCheck(userId));
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> userDelete(@RequestParam(value = "idList[]", defaultValue = "") List<Long> idList){
        return ResponseEntity.ok(userService.daleteUsers(idList));
    }

    @PutMapping("/password-change")
    public ResponseEntity<Boolean> passwordChange(@RequestParam(value = "userId") String userId, @RequestParam(value = "userPwd") String userPwd){
        return ResponseEntity.ok(userService.passwordChange(userId, userPwd));
    }

}
