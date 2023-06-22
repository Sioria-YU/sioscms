package com.project.sioscms.apps.attach.service;

import com.project.sioscms.common.utils.common.secure.AesCryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachFileService {

    public String encryptTest(String plainText) throws Exception {
        return AesCryptoUtil.encrypt(plainText);
    }
}
