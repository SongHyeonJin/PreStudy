package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String userEmail;
    private String userPwd;
    private String userPwdChk;
    private String userName;
    private String userHp;
    private String userAddr;
    private String userAddrDetail;
    private int userDangdo;
    private String userProfile;

}
