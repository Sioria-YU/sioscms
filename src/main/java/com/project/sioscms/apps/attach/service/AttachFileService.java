package com.project.sioscms.apps.attach.service;

import com.google.common.collect.Lists;
import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFile;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.domain.repository.AttachFileGroupRepository;
import com.project.sioscms.apps.attach.domain.repository.AttachFileRepository;
import com.project.sioscms.common.service.AesCryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

//TODO : 파일 디렉토리 년월, 프로그램 구분 등 구조 변경 해야함.
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachFileService extends EgovAbstractServiceImpl {
    private final AttachFileGroupRepository attachFileGroupRepository;
    private final AttachFileRepository attachFileRepository;

    private final AesCryptoService aesCryptoService;

    @Value("${attach.delete.enabled}")
    private boolean attachDeleteEnabled;

    @Value("${attach.path}")
    private String ATTACH_PATH;

    //region file upload
    @Transactional
    public ResponseEntity<?> upload(MultipartFile file) throws Exception {
        return upload(file, null, null);
    }

    @Transactional
    public ResponseEntity<?> upload(MultipartFile file, AttachFileGroup attachFileGroup) throws Exception {
        return upload(file, attachFileGroup, null);
    }

    @Transactional
    public ResponseEntity<?> upload(MultipartFile file, AttachFileGroup attachFileGroup, String programId) throws Exception {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(originalFileName);
        }

        //원본 파일을 얻어온다.
        File originFile = new File(ATTACH_PATH + File.separator + "tmp" + File.separator + originalFileName);
        //파일 생성 경로 확인
        if(!originFile.exists()){
            if(!originFile.mkdir()){
                log.error("Directory create failed!!!!!");
            }
        }
        //업로드 임시파일 생성
        file.transferTo(originFile);

        //storage path / programId / year / month / day / file name
        String programPath = "";
        if(!ObjectUtils.isEmpty(programId)){
            programPath = programId + File.separator;
        }

        LocalDate now = LocalDate.now();
        String datePath = now.getYear() + File.separator + now.getMonthValue() + File.separator + now.getDayOfMonth() + File.separator;
        String fileFullPath = ATTACH_PATH + File.separator + programPath + datePath;

        //디렉토리 생성
        File fileFullPathDir = new File(fileFullPath);
        Files.createDirectories(fileFullPathDir.toPath());

        //암호화 하여 저장할 파일을 생성한다.
        String destinationFileName = System.nanoTime() + "_" + originalFileName;
        String encryptFileName = Base64.encodeBase64URLSafeString(destinationFileName.getBytes(StandardCharsets.UTF_8));
        File destination = new File(fileFullPath + encryptFileName);
        if(!destination.createNewFile()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
        }

        boolean isWrite = aesCryptoService.encryptFile(originFile, destination);

        if(isWrite){
            long fileOrderNum = 0L;
            if(attachFileGroup == null) {
                attachFileGroup = new AttachFileGroup();
            }else{
                //파일 순서 채번(현재 등록 개수(1개이상) + 1)
                fileOrderNum = attachFileRepository.countByAttachFileGroupAndIsDeleted(attachFileGroup, false);
                if(fileOrderNum > 0){
                    fileOrderNum++;
                }
            }
            attachFileGroup.setIsDeleted(false);

            //파일 정보 db 저장
            AttachFile attachFile = new AttachFile();
            attachFile.setAttachFileGroup(attachFileGroup);
            attachFile.setFileName(encryptFileName);
            attachFile.setOriginFileName(originalFileName);
            attachFile.setFileExtension(originalFileName.substring(originalFileName.lastIndexOf(".")+1));
            attachFile.setFilePath(fileFullPath);
            attachFile.setFileOrderNum(fileOrderNum);
            attachFile.setIsDeleted(false);
            attachFile.setIsUsed(true);
            attachFile.setFileSize(file.getSize());

            //attachFileGroup을 새로 만든 경우 건너뛰어야함
            List<AttachFile> attachFileList = attachFileRepository.findAllByAttachFileGroupAndIsDeleted(attachFileGroup, false);

            if(attachFileList == null){
                attachFileList = Lists.newArrayList();
            }

            attachFileList.add(attachFile);
            attachFileGroup.setAttachFileList(attachFileList);

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

    //region multiUpload
    @Transactional
    public AttachFileGroupDto.Response multiUpload(List<MultipartFile> files, Long attachFileGroupId, String programId){
        AttachFileGroup attachFileGroup = null;
        if(attachFileGroupId != null) {
            attachFileGroup = attachFileGroupRepository.findById(attachFileGroupId).orElse(null);
        }
        if(attachFileGroup == null){
            attachFileGroup = new AttachFileGroup();
            attachFileGroup.setIsDeleted(false);
            attachFileGroupRepository.save(attachFileGroup);
        }

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    upload(file, attachFileGroup, programId);
                }
            }
            return attachFileGroup.toResponse();
        }catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    //endregion

    //region file download
    public void download(String fileName, HttpServletResponse response){
        //파일명으로 파일 조회
        //암호화 파일 경로, 복호화 파일명 얻어옴
        AttachFile attachFile = attachFileRepository.findByFileName(fileName).orElse(null);

        if(attachFile == null){
            return;
        }

        //파일을 읽어옴(inputStream)
        File encryptFile = new File(attachFile.getFilePath() + attachFile.getFileName());    //파일 조회 시 들어있는 경로로 변경해야함
        String originalFileName = attachFile.getOriginFileName();

        try {
            //response output
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(originalFileName, StandardCharsets.UTF_8));
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
        AttachFile attachFile = attachFileRepository.findByFileName(fileName).orElse(null);
        if(attachFile == null){
            return;
        }

        //deleteMode : 삭제 모드(N 안함, D 삭제)
        //attachDeleteEnabled : 삭제 허용 설정
        if(attachDeleteEnabled && "D".equals(deleteMode)) {
            //파일 삭제 처리
            File originFile = new File(attachFile.getFilePath() + attachFile.getFileName());
            if(originFile.isFile()){
                if(!originFile.delete()){
                    log.error("File delete failed!!!!!");
                }
            }
        }
        //db 파일 삭제처리
        attachFile.setIsDeleted(true);
        attachFile.setIsUsed(false);
        attachFileRepository.save(attachFile);
    }
    //endregion

    //region getImage
    public void getImage(String fileName, HttpServletResponse response){
        //파일명으로 파일 조회
        //암호화 파일 경로, 복호화 파일명 얻어옴
        AttachFile attachFile = attachFileRepository.findByFileName(fileName).orElse(null);

        if(attachFile == null){
            return;
        }

        //파일을 읽어옴(inputStream)
        File encryptFile = new File(attachFile.getFilePath() + attachFile.getFileName());    //파일 조회 시 들어있는 경로로 변경해야함

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
