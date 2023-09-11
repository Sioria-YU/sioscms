package com.project.sioscms.apps.code.controller;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.service.CodeService;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/api/code")
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/list")
    public ResponseEntity<List<CodeDto.Response>> getCodeList(CodeDto.Request dto){
        return ResponseEntity.ok(codeService.getCodeList(dto));
    }

    @GetMapping("/{codeId}")
    public ResponseEntity<CodeDto.Response> getCode(@PathVariable("codeId") String codeId){
//        return ResponseEntity.ok(codeService.getCode(codeId));
        return ResponseEntity.ok(null);
    }

    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/save")
    public ResponseEntity<CodeDto.Response> saveCode(CodeDto.Request dto){
        return ResponseEntity.ok(codeService.saveCode(dto));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("/update")
    public ResponseEntity<CodeDto.Response> updateCode(CodeDto.Request dto){
        return ResponseEntity.ok(codeService.updateCode(dto));
    }

    @Auth(role = Auth.Role.ADMIN)
    @DeleteMapping("/delete/{codeId}")
    public ResponseEntity<Boolean> deleteCode(@PathVariable("codeId") String codeId){
        return null;
    }

    @Auth(role = Auth.Role.ADMIN)
    @DeleteMapping("/mulitple-delete")
    public ResponseEntity<Boolean> multipleDeleteCode(@RequestParam("codeGroupId") String codeGroupId, @RequestParam("codeIdList[]") String[] codeIdList){
        return ResponseEntity.ok(codeService.multipleDeleteCode(codeGroupId, codeIdList));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/duplication-check")
    public ResponseEntity<Boolean> duplicationCheck(@RequestParam("codeGroupId") String codeGroupId, @RequestParam("codeId") String codeId){
        return ResponseEntity.ok(codeService.duplicationCheck(codeGroupId, codeId));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("order-swap")
    public ResponseEntity<Boolean> orderSwap(@RequestParam("codeGroupId") String codeGroupId, @RequestParam("codeId1") String codeId1, @RequestParam("codeId2") String codeId2){
        return ResponseEntity.ok(codeService.orderSwapUpdate(codeGroupId, codeId1, codeId2));
    }
}
