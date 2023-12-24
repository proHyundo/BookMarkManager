package com.hyun.bookmarkshare.user.service;

import com.hyun.bookmarkshare.exceptions.domain.user.UserLoginException;
import com.hyun.bookmarkshare.exceptions.domain.user.UserLogoutException;
import com.hyun.bookmarkshare.exceptions.domain.user.UserProcessException;
import com.hyun.bookmarkshare.exceptions.errorcode.RefreshTokenErrorCode;
import com.hyun.bookmarkshare.exceptions.errorcode.UserErrorCode;
import com.hyun.bookmarkshare.security.jwt.util.JwtTokenizer;
import com.hyun.bookmarkshare.smtp.dao.EmailRepository;
import com.hyun.bookmarkshare.smtp.entity.EmailEntity;
import com.hyun.bookmarkshare.smtp.exception.EmailExceptionErrorCode;
import com.hyun.bookmarkshare.smtp.exception.EmailProcessException;
import com.hyun.bookmarkshare.user.dao.TokenRepository;
import com.hyun.bookmarkshare.user.dao.UserRepository;
import com.hyun.bookmarkshare.user.entity.User;
import com.hyun.bookmarkshare.user.entity.UserRefreshToken;
import com.hyun.bookmarkshare.user.service.request.LoginServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.request.UserSocialSignUpServiceRequestDto;
import com.hyun.bookmarkshare.user.service.response.UserLoginResponse;
import com.hyun.bookmarkshare.user.service.response.UserResponse;
import com.hyun.bookmarkshare.user.service.response.UserSignoutResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PwdEncoder pwdEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final EmailRepository emailRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    @Override
    public UserResponse signUp(UserSignUpServiceRequestDto signUpRequestDto) {

        // 1. check if email is not valid
        EmailEntity targetEmail = emailRepository.findByEmail(signUpRequestDto.getUserEmail())
                .orElseThrow(() -> new EmailProcessException(EmailExceptionErrorCode.EMAIL_NOT_FOUND));
        if(targetEmail.getEmailValidFlag() != 1){
            throw new EmailProcessException(EmailExceptionErrorCode.EMAIL_NOT_VALID);
        }

        User newUser = signUpRequestDto.toEntity();

        // 2. encode pwd
        try {
            String encodedPwd = pwdEncoder.encode(signUpRequestDto.getUserPwd());
            newUser.setUserPwd(encodedPwd);
        } catch (NoSuchAlgorithmException e) {
            log.warn("[오류][UserServiceImpl.signUp().encodePwd]"+e.getMessage());
            throw new RuntimeException("Failed to encode password", e);
        }


        // 3. save user
        int resultRows = userRepository.saveNew(newUser);
        if(resultRows != 1){
            throw new UserLoginException(UserErrorCode.USER_DB_RESULT_WRONG, UserErrorCode.USER_DB_RESULT_WRONG.getMessage());
        }

        // 4. delete email validation
        emailRepository.deleteByEmail(signUpRequestDto.getUserEmail());

        // 5. return signUp success User Entity
        return UserResponse.of(userRepository.findByUserId(newUser.getUserId())
                .orElseThrow(() -> new UserLoginException(UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage())));
    }

    @Transactional
    @Override
    public UserResponse signUpBySocialAccount(UserSocialSignUpServiceRequestDto requestDto){
        User newUserBySocialAccount = requestDto.toEntity();
        try {
            newUserBySocialAccount.setUserPwd(pwdEncoder.encode(requestDto.getSocialUserPwd()));
        } catch (NoSuchAlgorithmException e) {
            log.warn("[오류][UserServiceImpl.signUpBySocialAccount().encodePwd]"+e.getMessage());
            throw new RuntimeException("Failed to encode password", e);
        }
        int resultRows = userRepository.saveNew(newUserBySocialAccount);
        if(resultRows != 1){
            throw new UserLoginException(UserErrorCode.USER_DB_RESULT_WRONG, UserErrorCode.USER_DB_RESULT_WRONG.getMessage());
        }
        return UserResponse.of(userRepository.findByUserId(newUserBySocialAccount.getUserId())
                .orElseThrow(() -> new UserLoginException(UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage())));
    }


    @Override
    public boolean checkDuplicateEmail(String userEmail) {
        // check if user already exist
        if(userRepository.countByUserEmail(userEmail) > 0){
            throw new UserLoginException(UserErrorCode.USER_SIGNIN_ALREADY_EXIST, UserErrorCode.USER_SIGNIN_ALREADY_EXIST.getMessage());
        }
        return false;
    }

    @Override
    public Boolean checkRegisteredEmail(String userEmail, String provider) {
        if(userRepository.countByUserEmail(userEmail) == 1){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public UserLoginResponse loginProcess(LoginServiceRequestDto loginRequestDto) {

        // 0. encode pwd & set encoded pwd to loginRequestDto
        // -> will depreciate as switch to security pwd encode
        try {
            loginRequestDto.setPwd(pwdEncoder.encode(loginRequestDto.getPwd()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 1. DB select & return User Entity
        User resultUser = userRepository.findByLoginServiceRequestDto(loginRequestDto)
                .orElseThrow(() -> new UserLoginException(UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage()));

        // 2. Generate access and refresh tokens by JWT library
        List<String> userRoles = List.of(resultUser.getUserRole());
        String accessToken = jwtTokenizer.createAccessToken(resultUser.getUserId(), resultUser.getUserEmail(), userRoles);
        String refreshToken = jwtTokenizer.createRefreshToken(resultUser.getUserId(), resultUser.getUserEmail(), userRoles);

        // 3. Set tokens to User Entity
        resultUser.setUserAccessToken(accessToken);
        resultUser.setUserRefreshToken(refreshToken);
        log.info("user refresh token set : {}", resultUser.getUserRefreshToken());
        // 4. Save Refresh token to DB Whitelist
        int resultRows = 0;
        if (tokenRepository.findByUserId(resultUser.getUserId()) == 1) {
            resultRows = tokenRepository.updateRefreshToken(resultUser.getUserId(), resultUser.getUserRefreshToken());
        }else{
            resultRows = tokenRepository.saveRefreshToken(resultUser.getUserId(), resultUser.getUserRefreshToken());
        }
        if(resultRows != 1) throw new UserLoginException(UserErrorCode.USER_DB_RESULT_WRONG,
                UserErrorCode.USER_DB_RESULT_WRONG.getMessage());

        return UserLoginResponse.builder().userId(resultUser.getUserId())
                .userEmail(resultUser.getUserEmail())
                .userRole(resultUser.getUserRole())
                .userAccessToken(resultUser.getUserAccessToken())
                .userRefreshToken(resultUser.getUserRefreshToken())
                .build();
    }

    @Transactional
    @Override
    public void logoutProcess(String refreshToken) {
        // DB 에서 RefreshToken 존재 확인
        UserRefreshToken userRefreshToken = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UserLogoutException(RefreshTokenErrorCode.RT_NOT_FOUND));

        int deletedRows = tokenRepository.deleteRefreshTokenByUserId(userRefreshToken.getUserId());
        if(deletedRows != 1) throw new UserLogoutException(RefreshTokenErrorCode.RT_DB_RESULT_WRONG);
    }

    @Override
    public String extendLoginState(String refreshToken) {
        // 전달받은 RefreshToken 이 DB에 저장되어 있는지 확인. 존재하지 않다면 에러
        if(tokenRepository.countByRefreshToken(refreshToken) != 1) throw new IllegalArgumentException("Refresh token not found");

        // 토큰이 존재한다면, refresh token 을 jwtTokenizer 에게 전달하여 토큰 검증 + claims 를 반환받음. 검증 실패 시 에러 + login page 로 리다이렉트.
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);

        // 검증에 성공하였다면, 반환받은 claims 에서 사용자 식별번호, 권한리스트, 이메일을 꺼냄. JwtTokenizer 에서 userId 라고 key 값을 정했음.
        Long userId = claims.get("userId", Long.class);
        List roles = claims.get("roles", List.class);
        String email = claims.getSubject();

        // 꺼내받은 식별번호로 사용자가 존재하는지 조회.
        userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 사용자 식별번호, 권한, 이메일로 새로운 액세스 토큰을 생성.
        // 생성한 액세스 토큰을 사용자에게 응답.
        return jwtTokenizer.createAccessToken(userId, email, roles);
    }

    @Override
    public UserResponse getUserInfo(Long userId) {
        return UserResponse.of(userRepository.findByUserId(userId).orElseThrow(
                () -> new UserProcessException(UserErrorCode.USER_NOT_FOUND)
        ));
    }

    @Override
    public UserSignoutResponse signOut(String accessToken, String userEmail) {
        Long userIdFromToken = jwtTokenizer.getUserIdFromAccessToken(accessToken);
        // userEmail 로 User 조회
        User targetUser = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new UserProcessException(UserErrorCode.USER_NOT_FOUND)
        );
        // 조회한 User 의 userId 와 token 에서 추출한 userId 가 일치하는지 확인
        if(!targetUser.getUserId().equals(userIdFromToken)){
            throw new UserProcessException(UserErrorCode.USER_NO_AUTH);
        }
        // TODO : 회원탈퇴 처리 전, 로그아웃 로직 추가.

        // 일치할 경우, User State 를 'e' 로 변경
        int resultRows = userRepository.deleteByUserId(userIdFromToken);
        if(resultRows != 1){
            throw new UserProcessException(UserErrorCode.USER_DB_RESULT_WRONG);
        }
        targetUser.setUserState("e");
        return UserSignoutResponse.of(targetUser.getUserEmail(), targetUser.getUserState());
    }


}
