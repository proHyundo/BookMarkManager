package com.hyun.bookmarkshare.smtp;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class EmailEntity {

    private String email;
    private String code;
    private Integer sendCount;
    private Date sendDate;
}
