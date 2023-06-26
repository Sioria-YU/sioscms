package com.project.sioscms.apps.attach.service;

import com.project.sioscms.common.service.AesCryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachFileService {

    private final AesCryptoService aesCryptoService;

    @Value("${attach.delete.enabled}")
    private boolean attachDeleteEnabled;

    @Value("${attach.path}")
    private String attachPath;

    public String encryptTest(String inputText) throws Exception {
        return aesCryptoService.encrypt(inputText);
    }

    public String decryptTest(String inputText) throws Exception {
        return aesCryptoService.decrypt(inputText);
    }

    public ResponseEntity upload(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        //원본 파일을 얻어온다.
        File originFile = new File(attachPath + File.separator + "tmp" + File.separator + originalFileName);
        //파일 생성 경로 확인
        if(!originFile.exists()){
            originFile.mkdir();
        }

        file.transferTo(originFile);

        //암호화 하여 저장할 파일을 생성한다.
        File destination = new File(attachPath + File.separator + aesCryptoService.encrypt(originalFileName));
        if(!destination.createNewFile()){
            destination = new File(attachPath + File.separator + aesCryptoService.encrypt(originalFileName + System.nanoTime()));
            destination.createNewFile();
        }

        boolean isWrited = aesCryptoService.encryptFile(originFile, destination);


        if(isWrited){
            originFile.delete();
            return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
    }

    public Boolean download(String fileName) throws Exception{

        return true;
    }
}
