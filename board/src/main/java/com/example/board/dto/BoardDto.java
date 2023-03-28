package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private int boardSeq;
    private String boardTitle;
    private String boardContent;
    private String boardPrice;
    private int boardSort;
    private int boardLike;
    private String BoardEmail;
    private String boardImg;
    private String boardVideo;
    private int boardCnt;
    private String boardDate;
}
