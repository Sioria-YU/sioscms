package com.project.sioscms.common.service;

import com.project.sioscms.common.utils.common.secure.AesCryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Service
public class AesCryptoService {

    @Value("${aes.secret-key}")
    private String SECRET_KEY;

    @Value("${aes.iv-key}")
    private String IV_KEY;

    @Value("${aes.spec-name}")
    private String SPEC_NAME;

    public String encrypt(String plainText) throws Exception {
        return AesCryptoUtil.encrypt(SECRET_KEY, IV_KEY, SPEC_NAME, plainText);
    }

    public String decrypt(String plainText) throws Exception {
        return AesCryptoUtil.decrypt(SECRET_KEY, IV_KEY, SPEC_NAME, plainText);
    }

    public Boolean encryptFile(File attachFile, File createFile) throws Exception {
        return AesCryptoUtil.encryptFile(SECRET_KEY, IV_KEY, SPEC_NAME, attachFile, createFile);
    }

    public Boolean decryptFile(File encryptFile, HttpServletResponse response) throws Exception {
        return AesCryptoUtil.decryptFile(SECRET_KEY, IV_KEY, SPEC_NAME, encryptFile, response);
    }
}
