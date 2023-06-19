package com.project.sioscms.apps.code.controller;

import com.project.sioscms.apps.code.domain.dto.CodeDto;
import com.project.sioscms.apps.code.service.CodeService;
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
        return ResponseEntity.ok(codeService.getCode(codeId));
    }

    @PostMapping("/save")
    public ResponseEntity<CodeDto.Response> saveCode(CodeDto.Request dto){
        return ResponseEntity.ok(codeService.saveCode(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<CodeDto.Response> updateCode(CodeDto.Request dto){
        return ResponseEntity.ok(codeService.updateCode(dto));
    }

    @DeleteMapping("/delete/{codeId}")
    public ResponseEntity<Boolean> deleteCode(@PathVariable("codeId") String codeId){
        return null;
    }

    @DeleteMapping("/mulitple-delete")
    public ResponseEntity<Boolean> multipleDeleteCode(@RequestParam("codeIdList[]") String[] codeIdList){
        return ResponseEntity.ok(codeService.multipleDeleteCode(codeIdList));
    }

    @PostMapping("/duplication-check")
    public ResponseEntity<Boolean> duplicationCheck(@RequestParam("codeId") String codeId){
        return ResponseEntity.ok(codeService.duplicationCheck(codeId));
    }

    @PutMapping("order-swap")
    public ResponseEntity<Boolean> orderSwap(@RequestParam("codeGroupId") String codeGroupId, @RequestParam("codeId1") String codeId1, @RequestParam("codeId2") String codeId2){
        return ResponseEntity.ok(codeService.orderSwapUpdate(codeGroupId, codeId1, codeId2));
    }
}
