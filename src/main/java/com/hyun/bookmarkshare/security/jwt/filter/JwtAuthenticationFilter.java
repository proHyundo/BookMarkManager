package com.hyun.bookmarkshare.security.jwt.filter;

import com.hyun.bookmarkshare.security.jwt.exception.JwtExceptionCode;
import com.hyun.bookmarkshare.security.jwt.token.JwtAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Permitted Request 인 경우 Header 의 Authorization 속성에 토큰이 담겨있지 않다.
//        if(isPermitRequest(request)){
//            // 허용된 요청일 경우 SecurityContext 에 인증 객체를 넣지 않는다.
//            // doFilter : 다음 필터로 넘어가는 것 (참고 : https://jun-itworld.tistory.com/28)
//            filterChain.doFilter(request, response);
//            return;
//        };

        // http header의 token 가져온다.
        String token="";
        try {
            token = getToken(request);
            if (StringUtils.hasText(token)) {
                getAuthentication(token);
            }
            filterChain.doFilter(request, response);
        }
        catch (NullPointerException | IllegalStateException e) {
            request.setAttribute("exception", JwtExceptionCode.NOT_FOUND_TOKEN.getCode());
            log.error("Not found Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new not found token exception");
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            log.error("Invalid Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new invalid token exception");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.EXPIRED_TOKEN.getCode());
            log.error("EXPIRED Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new expired token exception");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
            log.error("Unsupported Token // token : {}", token);
            log.error("Set Request Exception Code : {}", request.getAttribute("exception"));
            throw new BadCredentialsException("throw new unsupported token exception");
        } catch (Exception e) {
            log.error("====================================================");
            log.error("JwtFilter - doFilterInternal() 오류 발생");
            log.error("token : {}", token);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("====================================================");
            throw new BadCredentialsException("throw new exception");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] permitUrls = {"/api/v1/email/verification/check", "/api/v1/user/signup", "/api/v1/user/login",
                                "/api/v1/user/refresh", "/api/v1/user/email/check"};
        return Arrays.asList(permitUrls).contains(request.getRequestURI());
    }

    private void getAuthentication(String token) {
        // 헤더로 받은 token 으로 JwtAuthenticationToken 타입 객체 생성
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
        // 생성한 JwtAuthenticationToken 타입의 토큰을 AuthenticationManager 에게 검증을 요청
        // AuthenticationManager 는 Provider를 호출하여 실질적인 검증(인증)을 수행
        // JwtAuthenticationProvider 에서 AuthenticationManager 인터페이스의 authenticate 메서드를 구현했음.
        authenticationManager.authenticate(authenticationToken);
        // 검증에 통과하여 예외가 발생되지 않았다면, Context에 인증정보 담는다.
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

    // http header 의 Authorization 속성에서 토큰을 추출
    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")){
            String[] arr = authorization.split(" ");
            return arr[1];
        }
        return null;
    }

//    private boolean isPermitRequest(HttpServletRequest request) {
//        String uri = request.getRequestURI();
//        if (uri.equals("/signup/email/verification") || uri.equals("/signup/email/verification/check")) {
//            return true;
//        }
//        return false;
//    }
}
