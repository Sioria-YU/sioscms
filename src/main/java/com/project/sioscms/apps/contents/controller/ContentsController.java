package com.project.sioscms.apps.contents.controller;

import com.project.sioscms.apps.attach.domain.dto.AttachFileDto;
import com.project.sioscms.apps.contents.domain.dto.ContentsDto;
import com.project.sioscms.apps.contents.service.ContentsService;
import com.project.sioscms.secure.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @Auth(role = Auth.Role.ADMIN)
    @GetMapping("/compare-history")
    public ResponseEntity<Map<String, String>> getCompareHistory(@RequestParam("historyId") final Long historyId){
        return ResponseEntity.ok(contentsService.getCompareHistory(historyId));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("/save-attach-files")
    public ResponseEntity<List<AttachFileDto.Response>> saveAttachFiles(ContentsDto.Request requestDto, @RequestPart List<MultipartFile> files){
        return ResponseEntity.ok(contentsService.saveAttachFiles(requestDto, files));
    }

    @Auth(role = Auth.Role.ADMIN)
    @PutMapping("/change-version")
    public ResponseEntity<Boolean> changeVersion(@RequestParam("id") final Long id, @RequestParam("historyId") final Long historyId){
        return ResponseEntity.ok(contentsService.changeVersion(id, historyId));
    }

}
