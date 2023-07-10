package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 20)
    private String pwd;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Builder
    public LoginRequestDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public LoginServiceRequestDto toServiceDto() {
        return LoginServiceRequestDto.builder()
                .email(this.email)
                .pwd(this.pwd)
                .build();
    }
}
