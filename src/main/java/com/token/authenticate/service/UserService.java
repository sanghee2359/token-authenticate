package com.token.authenticate.service;

import com.token.authenticate.domain.User;
import com.token.authenticate.domain.dto.UserJoinResponse;
import com.token.authenticate.exception.AppException;
import com.token.authenticate.exception.ErrorCode;
import com.token.authenticate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public String join(String userName, String password) {
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
}
