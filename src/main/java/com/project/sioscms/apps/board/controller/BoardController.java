package com.project.sioscms.apps.board.controller;

import com.project.sioscms.apps.board.domain.dto.BoardDto;
import com.project.sioscms.apps.board.service.BoardService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/getList")
    public ResponseEntity<SiosPage<BoardDto.Response>> getBoardList(BoardDto.Request requestDto){
        return ResponseEntity.ok(boardService.getBoardList(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto.Response> getBoard(@PathVariable Long id){
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @PostMapping("/save")
    public ResponseEntity<BoardDto.Response> saveBoard(BoardDto.Request requestDto){
        return ResponseEntity.ok(boardService.saveBoard(requestDto));
    }

    @PutMapping("/update")
    public ResponseEntity<BoardDto.Response> updateBoard(BoardDto.Request requestDto){
        return ResponseEntity.ok(boardService.updateBoard(requestDto));
    }

    @PutMapping("/hit/{id}")
    public ResponseEntity<Boolean> hitBoard(@PathVariable Long id){
        return ResponseEntity.ok(boardService.updateBoardViewCount(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long id){
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }

}
