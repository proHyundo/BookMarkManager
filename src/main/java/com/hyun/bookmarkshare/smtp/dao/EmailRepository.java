package com.hyun.bookmarkshare.smtp.dao;

import com.hyun.bookmarkshare.smtp.EmailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface EmailRepository {

    Optional<EmailEntity> findByEmailAndValidationCode(String email, String emailValidationCode);

    Integer countByUserEmail(String userEmail);

    Integer saveByEmailAndValidationCode(String email, String validationCode);

    int deleteByValidatedEmail(String email);
}
