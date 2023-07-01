package com.hyun.bookmarkshare.smtp.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class EmailEntity {

    private String email;
    private String emailCode;
    private Integer sendCnt;
    private Date sendDate;
    private int emailValidFlag;
}
