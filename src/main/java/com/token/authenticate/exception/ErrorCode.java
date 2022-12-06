package com.token.authenticate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    USERNAME_NOTFOUND(HttpStatus.NOT_FOUND,"" );
    private HttpStatus httpStatus;
    private String message;
}
