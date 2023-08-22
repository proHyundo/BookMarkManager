package com.hyun.bookmarkshare.utils;

import com.hyun.bookmarkshare.exceptions.errorcode.CustomErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String statusDescription;
    private final String message;

    public ApiErrorResponse(int statusCode, String statusDescription, String message) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.message = message;
    }

    public static ApiErrorResponse of(CustomErrorCode errorCode) {
        return new ApiErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().name(),
                errorCode.getMessage()
        );
    }

}
