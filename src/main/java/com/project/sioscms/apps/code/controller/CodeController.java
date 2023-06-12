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
        return null;
    }

    @PutMapping("/update")
    public ResponseEntity<CodeDto.Response> updateCode(CodeDto.Request dto){
        return null;
    }

    @PutMapping("/delete/{codeId}")
    public ResponseEntity<Boolean> deleteCode(@PathVariable("codeId") String codeId){
        return null;
    }
}