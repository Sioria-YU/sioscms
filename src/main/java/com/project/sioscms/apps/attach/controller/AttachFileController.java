package com.project.sioscms.apps.attach.controller;

import com.project.sioscms.apps.attach.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attach")
@RequiredArgsConstructor
public class AttachFileController {

    private final AttachFileService attachFileService;

    @GetMapping("/encrypt-test")
    public ResponseEntity<String> encryptTest(@RequestParam("inputText") String inputText) throws Exception {
        return ResponseEntity.ok(attachFileService.encryptTest(inputText));
    }

    @GetMapping("/decrypt-test")
    public ResponseEntity<String> decryptTest(@RequestParam("inputText") String inputText) throws Exception {
        return ResponseEntity.ok(attachFileService.decryptTest(inputText));
    }
}
