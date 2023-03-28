package com.example.board.service;

import com.example.board.dto.BoardDeleteDto;
import com.example.board.dto.BoardDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.PopularSearchEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.PopularSearchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    PopularSearchRepository popularSearchRepository;

    public ResponseDto<?> boardWrite(BoardDto dto){
        String boardEmail = dto.getBoardEmail();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        String boardImg = dto.getBoardImg();
        String boardPrice = dto.getBoardPrice();

        // BoardEntity 생성
        BoardEntity boardEntity = new BoardEntity(dto);

        boardEntity.setBoardEmail(boardEmail);
        boardEntity.setBoardTitle(boardTitle);
        boardEntity.setBoardContent(boardContent);
        boardEntity.setBoardImg(boardImg);
        boardEntity.setBoardPrice(boardPrice);

        try {
            boardRepository.save(boardEntity);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("Data Base Error!");
        }

        return ResponseDto.setSuccess("Board Write Success", null);
    }

    public  ResponseDto<List<BoardEntity>> getList(){

        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        try {
            boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"boardSeq"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("DataBase Error");
        }
        return  ResponseDto.setSuccess("Success", boardList);

    }

    public ResponseDto<List<PopularSearchEntity>> getPopularSearchList() {

        List<PopularSearchEntity> popularSearchList = new ArrayList<PopularSearchEntity>();

        try {
            popularSearchList = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", popularSearchList);
    }

    public void deleteBoard(BoardDeleteDto dto) {
        int boardSeq = dto.getBoardSeq();
        boardRepository.deleteBoardEntityByBoardSeq(boardSeq);
//            List<BoardEntity> boardList = boardRepository.findAll();
//            return ResponseDto.setSuccess("Delete Success!", boardList);
    }

}
