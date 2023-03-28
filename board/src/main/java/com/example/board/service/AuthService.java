package com.example.board.service;

import com.example.board.dto.ResponseDto;
import com.example.board.dto.SignInDto;
import com.example.board.dto.SignInResponseDto;
import com.example.board.dto.SignUpDto;
import com.example.board.entity.UserEntity;
import com.example.board.repository.UserRepository;
import com.example.board.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseDto<?> signUp(SignUpDto dto){
        String userEmail = dto.getUserEmail();
        String userPwd = dto.getUserPwd();
        String userPwdChk = dto.getUserPwdChk();
        String userProfile = dto.getUserProfile();

        // email 중복 확인
        try{
            if(userRepository.existsById(userEmail))
                return ResponseDto.setFailed("Existed Email!");
        }catch(Exception e){
            return ResponseDto.setFailed("Data Base Error!");
        }

        // 비밀번호가 서로 다르면 failed response 반환!
        if(!userPwd.equals(userPwdChk))
            return ResponseDto.setFailed("Password does not matched!");

        // UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userPwd);
        userEntity.setUserPwd(encodedPassword);

        // 프로필 저장
        userEntity.setUserProfile(userProfile);

        // UserRepository를 이용해 데이터베이스에 Entity 저장!
        try{
            userRepository.save(userEntity);
        }catch (Exception e) {
            return ResponseDto.setFailed("Data Base Error!");
        }

        // 성공시 success response 반환
        return ResponseDto.setSuccess("Sign Up Success!",null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto){
        String userEmail = dto.getUserEmail();
        String userPwd = dto.getUserPwd();

        UserEntity userEntity=null;
        try{
            userEntity = userRepository.findByUserEmail(userEmail);
            // 잘못된 이메일
            if (userEntity == null) return ResponseDto.setFailed("Sign In Failed");
            // 잘못된 패스워드
            if (!passwordEncoder.matches(userPwd, userEntity.getUserPwd()))
                return ResponseDto.setFailed("Sign In Failed");
        }catch (Exception e){
            return ResponseDto.setFailed("Database Error");
        }

        userEntity.setUserPwd("");

        String token = tokenProvider.create(userEmail);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, userEntity);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }
}
