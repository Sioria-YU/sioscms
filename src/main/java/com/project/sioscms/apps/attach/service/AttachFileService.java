package com.project.sioscms.apps.attach.service;

import com.project.sioscms.common.service.AesCryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        //업로드 임시파일 생성
        file.transferTo(originFile);

        //암호화 하여 저장할 파일을 생성한다.
//        File destination = new File(attachPath + File.separator + aesCryptoService.encrypt(originalFileName));
        File destination = new File(attachPath + File.separator + Base64.encodeBase64URLSafeString(originalFileName.getBytes(StandardCharsets.UTF_8)));
        if(!destination.createNewFile()){
            destination = new File(attachPath + File.separator + aesCryptoService.encrypt(originalFileName + System.nanoTime()));
            destination.createNewFile();
        }

        boolean isWrited = aesCryptoService.encryptFile(originFile, destination);

        if(isWrited){
            //임시 파일 삭제처리
            if(!originFile.delete()){
                log.error(originFile.getName() + " unable to delete");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
    }

    public void download(String fileName, HttpServletResponse response) throws Exception{
        //파일명으로 파일 조회
        //암호화 파일 경로, 복호화 파일명 얻어옴

        //파일을 읽어옴(inputStream)
        File encryptFile = new File(attachPath + File.separator + fileName);    //파일 조회 시 들어있는 경로로 변경해야함
        String originalFileName = new String(Base64.decodeBase64(fileName), StandardCharsets.UTF_8);
        System.out.println("originalFileName :::::::>>>>> " + originalFileName);

        try {
            //response output
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; fileName='" + URLEncoder.encode(originalFileName, StandardCharsets.UTF_8) + "'");
            response.setHeader("Content-Transfer-Encoding", "binary");

            //파일 복호화 및 전송
            if(aesCryptoService.decryptFile(encryptFile, response)) {
//                return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
            }else{
                log.error("file decrypting failed!!!");
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
            }
        }catch (Exception e){
            log.error(e.toString());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
    }
}
