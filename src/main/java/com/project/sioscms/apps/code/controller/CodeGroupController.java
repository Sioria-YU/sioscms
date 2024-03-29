package com.project.sioscms.apps.code.controller;

import com.project.sioscms.apps.code.domain.dto.CodeGroupDto;
import com.project.sioscms.apps.code.service.CodeGroupService;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/api/code-group")
@RequiredArgsConstructor
public class CodeGroupController {
    private final CodeGroupService codeGroupService;

    @GetMapping("/list")
    public ResponseEntity<List<CodeGroupDto.Response>> getCodeGroupList(CodeGroupDto.Request dto){
        return ResponseEntity.ok(codeGroupService.getCodeGroupList(dto));
    }

    @GetMapping("/{codeGroupId}")
    public ResponseEntity<CodeGroupDto.Response> getCodeGroup(@PathVariable("codeGroupId") String codeGroupId){
        return ResponseEntity.ok(codeGroupService.getCodeGroup(codeGroupId));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/save")
    public ResponseEntity<CodeGroupDto.Response> saveCodeGroup(CodeGroupDto.Request dto){
        return ResponseEntity.ok(codeGroupService.saveCodeGroup(dto));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("/update")
    public ResponseEntity<CodeGroupDto.Response> updateCodeGroup(CodeGroupDto.Request dto){
        return ResponseEntity.ok(codeGroupService.updateCodeGroup(dto));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("/delete/{codeGroupId}")
    public ResponseEntity<Boolean> deleteCodeGroup(@PathVariable("codeGroupId") String codeGroupId){
        return ResponseEntity.ok(codeGroupService.deleteCodeGroup(codeGroupId));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/duplication-check")
    public ResponseEntity<Boolean> duplicationCheck(@RequestParam("codeGroupId") String codeGroupId){
        return ResponseEntity.ok(codeGroupService.duplicationCheck(codeGroupId));
    }

    @Auth(role = Auth.Role.ADMIN)
    @DeleteMapping("/mulitple-delete")
    public ResponseEntity<Boolean> multipleDelete(@RequestParam("codeGroupIdList[]") String[] codeGroupIdList){
        return ResponseEntity.ok(codeGroupService.multipleDelete(codeGroupIdList));
    }
}
