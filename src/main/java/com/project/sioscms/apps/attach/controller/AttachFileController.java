package com.project.sioscms.apps.attach.controller;

import com.project.sioscms.apps.attach.service.AttachFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/attach")
@RequiredArgsConstructor
public class AttachFileController {

    private final AttachFileService attachFileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart MultipartFile file) throws Exception {
        return ResponseEntity.ok(attachFileService.upload(file));
    }

    @PostMapping("/multi-upload")
    public ResponseEntity<?> multiUpload(@RequestPart List<MultipartFile> files) throws Exception {
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    attachFileService.upload(file);
                }
            }
            return ResponseEntity.ok(HttpStatus.CREATED);
        }catch (NullPointerException | IOException e){
            log.error("multiFile Upload Exception" + " ::::::::::>>> " + e.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {
        attachFileService.download(fileName, response);
    }

    @DeleteMapping("/delete/{fileName}")
    public void delete(@PathVariable("fileName") String fileName) throws Exception{
        attachFileService.delete(fileName);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("fileName") String fileName, @RequestParam("deleteMode") String deleteMode) throws Exception{
        attachFileService.delete(fileName, deleteMode);
    }

    @GetMapping("/get-image/{fileName}")
    public void getImage(@PathVariable("fileName") String fileName, HttpServletResponse response){
        attachFileService.getImage(fileName, response);
    }
}
