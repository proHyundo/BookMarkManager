package com.hyun.bookmarkshare.user.controller.dto;

import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpRequestDto {

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

    private String userPhoneNum;

    @NotEmpty
    @Pattern(regexp = "[mf]")
    private String userGender;

    @NotNull
    @Length(min = 8, max = 8)
    private String emailValidationCode;

    @Builder
    public UserSignUpRequestDto(Long userId, String userEmail, String userPwd, String userName, String userPhoneNum, String userGender, String emailValidationCode) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
        this.userGender = userGender;
        this.emailValidationCode = emailValidationCode;
    }

    public UserSignUpServiceRequestDto toServiceDto() {
        return UserSignUpServiceRequestDto.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userPwd(userPwd)
                .userName(userName)
                .userPhoneNum(userPhoneNum)
                .userGender(userGender)
                .emailValidationCode(emailValidationCode)
                .build();
    }

}
