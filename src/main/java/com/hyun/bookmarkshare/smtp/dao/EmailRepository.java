package com.hyun.bookmarkshare.smtp.dao;

import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface EmailRepository {

    /* ================================================================================================ */
    /* ============================================ SELECT ============================================ */

    Optional<EmailEntity> findByEmailAndValidationCode(String email, String emailCode);
    Optional<EmailEntity> findByEmail(String email);

    int countByUserEmail(String email);

    /* ================================================================================================ */
    /* ============================================ INSERT ============================================ */

    Integer saveByEmailAndValidationCode(String email, String emailCode);

    /* ================================================================================================ */
    /* ============================================ UPDATE ============================================ */

    int updateByEmailAndValidationCode(String email, String emailCode);
    int updateEmailValidationFlag(String email, String emailCode);

    /* ================================================================================================ */
    /* ============================================ DELETE ============================================ */

    int deleteByEmail(String email);
}
