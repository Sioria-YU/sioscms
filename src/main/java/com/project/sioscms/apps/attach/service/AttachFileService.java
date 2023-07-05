package com.project.sioscms.apps.attach.service;

import com.google.common.collect.Lists;
import com.project.sioscms.apps.attach.domain.entity.AttachFile;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.attach.domain.repository.AttachFileRepository;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachFileService {
    private final AttachFileGroupRepository attachFileGroupRepository;
    private final AttachFileRepository attachFileRepository;

    private final AesCryptoService aesCryptoService;

    @Value("${attach.delete.enabled}")
    private boolean attachDeleteEnabled;

    @Value("${attach.path}")
    private String attachPath;

    //region file upload
    @Transactional
    public ResponseEntity<?> upload(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(originalFileName);
        }

        //원본 파일을 얻어온다.
        File originFile = new File(attachPath + File.separator + "tmp" + File.separator + originalFileName);
        //파일 생성 경로 확인
        if(!originFile.exists()){
            if(!originFile.mkdir()){
                log.error("Directory create failed!!!!!");
            }
        }
        //업로드 임시파일 생성
        file.transferTo(originFile);

        //파일 저장 경로 세분화 로직 추가 필요
        String filePath = attachPath + File.separator;

        //암호화 하여 저장할 파일을 생성한다.
        String destinationFileName = System.nanoTime() + "_" + originalFileName;
        String encryptFileName = Base64.encodeBase64URLSafeString(destinationFileName.getBytes(StandardCharsets.UTF_8));
        File destination = new File(filePath + encryptFileName);
        if(!destination.createNewFile()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }

        boolean isWrite = aesCryptoService.encryptFile(originFile, destination);

        if(isWrite){
            AttachFileGroup attachFileGroup = new AttachFileGroup();
            attachFileGroup.setIsDeleted(false);

            //파일 정보 db 저장
            AttachFile attachFile = new AttachFile();
            attachFile.setAttachFileGroup(attachFileGroup);
            attachFile.setFileName(encryptFileName);
            attachFile.setOriginFileName(originalFileName);
            attachFile.setFileExtension(originalFileName.substring(originalFileName.lastIndexOf(".")+1));
            attachFile.setFilePath(filePath);
            attachFile.setFileOrderNum(0L);
            attachFile.setIsDeleted(false);
            attachFile.setIsUsed(true);
            attachFile.setFileSize(file.getSize());

            //권한 처리 후 제거
            attachFile.setCreatedBy(1L);
            attachFile.setUpdatedBy(1L);
            attachFileGroup.setCreatedBy(1L);
            attachFileGroup.setUpdatedBy(1L);

            List<AttachFile> attachFileList = Lists.newArrayList(attachFile);

            attachFileGroup.setAttachFile(attachFileList);

            //Entity save
            attachFileGroupRepository.save(attachFileGroup);
            attachFileRepository.save(attachFile);

            //임시 파일 삭제처리
            if(!originFile.delete()){
                log.error(originFile.getName() + " unable to delete");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }
    }
    //endregion

    //region file download
    public void download(String fileName, HttpServletResponse response){
        //파일명으로 파일 조회
        //암호화 파일 경로, 복호화 파일명 얻어옴

        //파일을 읽어옴(inputStream)
        File encryptFile = new File(attachPath + File.separator + fileName);    //파일 조회 시 들어있는 경로로 변경해야함
        String originalFileName = new String(Base64.decodeBase64(fileName), StandardCharsets.UTF_8);

        try {
            //response output
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; fileName='" + URLEncoder.encode(originalFileName, StandardCharsets.UTF_8) + "'");
            response.setHeader("Content-Transfer-Encoding", "binary");

            //파일 복호화 및 전송
            if(!aesCryptoService.decryptFile(encryptFile, response)) {
                log.error("file decrypting failed!!!");
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }
    //endregion

    //region file delete
    @Transactional
    public void delete(String fileName) throws Exception{
        this.delete(fileName, null);
    }

    @Transactional
    public void delete(String fileName, String deleteMode) throws Exception{
        //deleteMode : 삭제 모드(N 안함, D 삭제)
        //attachDeleteEnabled : 삭제 허용 설정
        if(attachDeleteEnabled && "D".equals(deleteMode)) {
            //파일 삭제 처리
            File originFile = new File(attachPath + File.separator + fileName);
            if(originFile.isFile()){
                if(!originFile.delete()){
                    log.error("File delete failed!!!!!");
                }
            }
        }
        //db 파일 삭제처리
        this.deleteAttachFile(fileName);
    }

    @Transactional
    protected void deleteAttachFile(String fileName){
        attachFileRepository.findByFileName(fileName).ifPresent(attachFile -> attachFile.setIsDeleted(true));
    }
    //endregion

    //region getImage
    public void getImage(String fileName, HttpServletResponse response){
        //파일명으로 파일 조회
        //암호화 파일 경로, 복호화 파일명 얻어옴

        //파일을 읽어옴(inputStream)
        File encryptFile = new File(attachPath + File.separator + fileName);    //파일 조회 시 들어있는 경로로 변경해야함

        try {
            //response output
            response.setHeader("Content-Type", "image/jpeg");   //이미지 타입 확장자에따라 바뀌도록 변경해야함

            //파일 복호화 및 전송
            if(!aesCryptoService.decryptFile(encryptFile, response)) {
                log.error("file decrypting failed!!!");
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }
    //endregion
}
