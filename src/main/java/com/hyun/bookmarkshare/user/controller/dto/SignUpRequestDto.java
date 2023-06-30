package com.hyun.bookmarkshare.user.controller.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Null
    @Positive
    private Long userId;

    @NotEmpty
    @Email
    private String userEmail;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String userPwd;

    @NotEmpty
    private String userName;

//    @NotNull
//    @PositiveOrZero
//    @Length(min = 10, max = 11)
    private String userPhoneNum;

    @NotEmpty
    @Pattern(regexp = "[mf]")
    private String userGender;

    @NotNull
    @Length(min = 8, max = 8)
    private String emailValidationCode;
}
