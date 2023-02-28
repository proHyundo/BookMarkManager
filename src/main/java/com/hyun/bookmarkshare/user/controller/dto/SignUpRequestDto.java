package com.hyun.bookmarkshare.user.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignUpRequestDto {

    @Null
    private Long userId;

    @NotEmpty
    @Email
    private String userEmail;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String userPwd;

    @NotNull
    @PositiveOrZero
    @Length(min = 3, max = 3)
    private Long userFirstPhoneNum;
    @NotNull
    @PositiveOrZero
    @Length(min = 3, max = 3)
    private Long userMiddlePhoneNum;
    @NotNull
    @PositiveOrZero
    @Length(min = 3, max = 3)
    private Long userLastPhoneNum;

    @NotEmpty
    @Pattern(regexp = "[mf]")
    private String userGender;
}
