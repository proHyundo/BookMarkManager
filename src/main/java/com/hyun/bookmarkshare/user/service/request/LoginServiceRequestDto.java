package com.hyun.bookmarkshare.user.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginServiceRequestDto {

    private String email;
    private String pwd;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Builder
    public LoginServiceRequestDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
