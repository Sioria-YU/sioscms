package com.project.sioscms.apps.board.controller;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.apps.board.service.BoardMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cms/api/board-master")
@RequiredArgsConstructor
public class BoardMasterController {
    private final BoardMasterService boardMasterService;

    @PostMapping("/save")
    public ResponseEntity<BoardMasterDto.Response> saveBoardMaster(BoardMasterDto.Request requestDto){
        return ResponseEntity.ok(boardMasterService.saveBoardMaster(requestDto));
    }
}
