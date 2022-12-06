package com.token.authenticate.service;

import com.token.authenticate.domain.User;
import com.token.authenticate.domain.dto.UserJoinResponse;
import com.token.authenticate.exception.AppException;
import com.token.authenticate.exception.ErrorCode;
import com.token.authenticate.repository.UserRepository;
import com.token.authenticate.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.secret}") // springframework
    private String key; // 키
    private Long expireTimeMs = 1000 * 60 * 60l; // expireTime : 1hour
    public String join(String userName, String password) {
        // userName이 이미 존재함
        userRepository.findByUserName(userName)
                .ifPresent(user->{
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED,userName+"은 이미 존재합니다.");
                });

        User user= User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .build();
        // save
        userRepository.save(user);
        return "SUCCESS";
    }

    public  String login(String userName, String password){
        // userName 없을경우
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(()->new AppException(ErrorCode.USERNAME_NOTFOUND, userName+"이 없습니다."));
        // password 틀릴경우
        if(!encoder.matches(selectedUser.getPassword(), password)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD,password+"가 틀렸습니다.");
        }
        // 예외가 없다면 토큰 발행
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(),key,expireTimeMs);
        return token;
    }
}
