package com.example.board.controller;

import com.example.board.dto.BoardDeleteDto;
import com.example.board.dto.BoardDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.PopularSearchEntity;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("/write")
    public ResponseDto<?> write(@RequestBody BoardDto requestBody){
        ResponseDto<?> result = boardService.boardWrite(requestBody);
        return result;
    }

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList(){
        return boardService.getList();
    }

    @GetMapping("/delete")
    public String deleteBoard(@RequestBody BoardDeleteDto requestBody) {
        boardService.deleteBoard(requestBody);
        return "redirect:/board";
    }

}
