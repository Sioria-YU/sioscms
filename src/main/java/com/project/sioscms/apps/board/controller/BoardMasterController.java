package com.project.sioscms.apps.board.controller;

import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.domain.entity.BoardMaster;
import com.project.sioscms.apps.board.service.BoardMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cms/api/board-master")
@RequiredArgsConstructor
public class BoardMasterController {
    private final BoardMasterService boardMasterService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardMasterDto.Response> getBoardMaster(@PathVariable Long id){
        return ResponseEntity.ok(boardMasterService.getBoardMaster(id));
    }

    @PostMapping("/save")
    public ResponseEntity<BoardMasterDto.Response> saveBoardMaster(BoardMasterDto.Request requestDto){
        return ResponseEntity.ok(boardMasterService.saveBoardMaster(requestDto));
    }

    @PutMapping("/update")
    public ResponseEntity<BoardMasterDto.Response> updateBoardMaster(BoardMasterDto.Request requestDto){
        return ResponseEntity.ok(boardMasterService.updateBoardMaster(requestDto));
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteBoardMaster(@PathVariable Long id){
        try {
            boardMasterService.deleteBoardMaster(id);
            return true;
        }catch (NullPointerException e){
            log.error(e.toString());
            return false;
        }
    }
}
