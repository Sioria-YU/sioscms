package com.project.sioscms.apps.attach.service;

import com.project.sioscms.common.service.AesCryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachFileService {

    private final AesCryptoService aesCryptoService;

    public String encryptTest(String inputText) throws Exception {
        return aesCryptoService.encrypt(inputText);
    }

    public String decryptTest(String inputText) throws Exception {
        return aesCryptoService.decrypt(inputText);
    }
}
