package com.example.board.entity;

import com.example.board.dto.BoardDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_BOARD")
@Table(name = "TB_BOARD")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardSeq;
    private String boardTitle;
    private String boardContent;
    private String boardPrice;
    private int boardSort;
    private int boardLike;
    private String boardEmail;
    private String boardImg;
    private String boardVideo;
    private int boardCnt;
    @CreationTimestamp
    @Column(name = "board_date")
    private LocalDateTime boardDate;

    public BoardEntity(BoardDto dto){
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
        this.boardPrice = dto.getBoardPrice();
        this.boardEmail = dto.getBoardEmail();
        this.boardImg = dto.getBoardImg();
    }

}
