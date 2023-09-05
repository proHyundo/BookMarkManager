package com.hyun.bookmarkshare.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactoryForTest.class)
public @interface WithCustomAuthUser {

    String email() default "test@test.com";
    int userId() default 1;
    String role() default "ROLE_USER";
}
