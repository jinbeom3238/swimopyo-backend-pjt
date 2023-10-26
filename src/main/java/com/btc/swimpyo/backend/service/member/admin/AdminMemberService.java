package com.btc.swimpyo.backend.service.member.admin;


import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.mappers.member.admin.IAdminMemberDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import com.btc.swimpyo.backend.utils.jwt.filter.JwtAuthenticationFilter;
import com.btc.swimpyo.backend.utils.jwt.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminMemberService implements IAdminMemberService{

    @Value("${secret-key}")
    private String secretKey;

    @Autowired
    IAdminMemberDaoMapper iAdminMemberDaoMapper;

    // @RequiredArgsConstructor을 이용하면 final로 지정된 것은 필수 생성자로 여긴다
    private final JwtProvider jwtProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //
    @Override
    public String signUp(Map<String, Object> msgMap, AdminMemberDto adminMemberDto) {
        System.out.println("[AuthServiceImplement] signUp");

        adminMemberDto.setA_m_email(msgMap.get("mail").toString());
        adminMemberDto.setA_m_pw(passwordEncoder.encode(msgMap.get("pw").toString()));
        adminMemberDto.setA_m_name(msgMap.get("name").toString());
        adminMemberDto.setA_m_phone(msgMap.get("phone").toString());
        adminMemberDto.setA_m_oper_yn(msgMap.get("a_m_oper_yn").toString());
        adminMemberDto.setA_m_br_yn(msgMap.get("a_m_br_yn").toString());
        adminMemberDto.setA_m_ar_yn(msgMap.get("a_m_ar_yn").toString());

        int result = -1;
        result = iAdminMemberDaoMapper.insertMember(adminMemberDto);
        if (result > 0) {
            System.out.println("SIGN UP SUCCESS");
            return "SIGN UP SUCCESS";
        } else {
            System.out.println("SIGN UP FAIL");
            return "SIGN UP FAIL";
        }

    }

    @Override
    public Map<String, Object> signIn(
            Map<String, Object> msgMap,
            AdminMemberDto adminMemberDto,
            RefTokenEntity refTokenEntity,
            HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("[AuthServiceImplement] signIn");

        adminMemberDto.setA_m_email(msgMap.get("email").toString());
        adminMemberDto.setA_m_pw(msgMap.get("pw").toString());

        log.info("1 = {}", adminMemberDto.getA_m_email());
        log.info("2 = {}", adminMemberDto.getA_m_pw());
        Map<String, Object> map = new HashMap<>();

        // 동일한 username 없음
        AdminMemberDto idVerifiedadminMemberDto = iAdminMemberDaoMapper.isMember(adminMemberDto);
        log.info("tp1");
        if (idVerifiedadminMemberDto != null && passwordEncoder.matches(adminMemberDto.getA_m_pw(), idVerifiedadminMemberDto.getA_m_pw())) {
            log.info("tp2");
            /*
                로그인 시
                동일한 refresh token 명의 행이 있다면 delete 후
                새로 발급받은 refresh token을 insert해준다.
             */
            final String authHeader = request.getHeader(HttpHeaders.COOKIE);
            final String checkingRefToken;
            if (authHeader != null) {
                log.info("tp3");
                String cookieToken = authHeader.substring(7);
                checkingRefToken = cookieToken.split("=")[1];
                refTokenEntity.setRef_token(checkingRefToken);
                log.info("checkingRefToken = {}", checkingRefToken);
                RefTokenEntity checkedRefToken = iAdminMemberDaoMapper.selectRefToken(refTokenEntity);
                if(checkedRefToken != null){
                    int result = iAdminMemberDaoMapper.deleteDupRefToken(checkedRefToken);
                    if(result > 0){
                        log.info("중복 refToken 삭제 완료");
                    } else {
                        log.info("중복 refToken 삭제 실패");
                    }
                }
//                map.put("result", HttpStatus.NOT_FOUND);
//                return map;
            }
            log.info("tp4");
            String accessToken = jwtProvider.createAccessToken(adminMemberDto.getA_m_email(), secretKey);
            String refreshToken = jwtProvider.createRefreshToken(adminMemberDto.getA_m_email(), secretKey);

            refTokenEntity.setRef_token(refreshToken);
            log.info("tp : "+ refTokenEntity.getRef_token());
            // refresh token -> tbl_tokens에 저장
            int result = iAdminMemberDaoMapper.insertRefToken(refTokenEntity);
            if(result <= 0){
                log.info("Ref Token 등록 실패");
            } else {
                log.info("Ref Token 등록 성공");

            }

            map.put("accessToken", accessToken);
            map.put("refreshToken", refreshToken);
            return map;

        } else {
            map.put("result", HttpStatus.NOT_FOUND);
            return map;

        }

    }

    @Override
    public Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity) {
        System.out.println("[AuthServiceImplement] refreshToken");

        Map<String, Object> map = new HashMap<>();
        final String authHeader = request.getHeader(HttpHeaders.COOKIE);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null) {
            map.put("result", HttpStatus.NOT_FOUND);
            return map;
        }
        String cookieToken = authHeader.substring(7);
        refreshToken = cookieToken.split("=")[1];

        // token에 동일한 refresh token 명이 있는지 check
        // 있으면 이후 작업 진행, -> DB 중복 ref token delete -> 새로 발급받은 ref token insert
        // 없으면 이미 로그아웃 또는 회원 탈퇴를 진행한 회원이라고 판단했기 때문에 오류 코드 발생.
        refTokenEntity.setRef_token(refreshToken);
        RefTokenEntity checkRefToken = iAdminMemberDaoMapper.selectRefToken(refTokenEntity);
        log.info("tp : {}", checkRefToken);
        if(checkRefToken == null) {
            map.put("result", "nullCheckRefToken");
            return map;
        }

        // 중복 ref token delete
        refTokenEntity.setRef_token(checkRefToken.getRef_token());
        log.info("checkRefToken = {}", checkRefToken);
        if(checkRefToken != null){
            int result = iAdminMemberDaoMapper.deleteDupRefToken(checkRefToken);
            if(result > 0){
                log.info("중복 refToken 삭제 완료");
            } else {
                log.info("중복 refToken 삭제 실패");
            }
        }

        userEmail = jwtAuthenticationFilter.getUserEmail(secretKey, refreshToken);
        if (userEmail != null) {
            if (jwtAuthenticationFilter.validate(secretKey, refreshToken)) {
                log.error("refreshToken이 만료되었습니다.");
//                jwtExceptionHandler(response, ErrorType.NOT_VALID_TOKEN);
            } else {


                // 재발급 받은 Ref Token insert
                String ReAccessToken = jwtProvider.createAccessToken(userEmail, secretKey);
                String ReRefreshToken = jwtProvider.createRefreshToken(userEmail, secretKey);

                refTokenEntity.setRef_token(ReRefreshToken);
                log.info("tp : "+ refTokenEntity.getRef_token());
                // refresh token -> tbl_tokens에 저장
                int result = iAdminMemberDaoMapper.insertRefToken(refTokenEntity);
                if(result <= 0){
                    log.info("Ref Token 등록 실패");
                } else {
                    log.info("Ref Token 등록 성공");

                }

                map.put("ReAccessToken", ReAccessToken);
                map.put("ReRefreshToken", ReRefreshToken);
                return map;
            }
        }
        return map;
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity) {
        log.info("logout");

        final String authHeader = request.getHeader(HttpHeaders.COOKIE);
        final String checkingRefToken;
        if (authHeader != null) {
            log.info("tp3");
            String cookieToken = authHeader.substring(7);
            checkingRefToken = cookieToken.split("=")[1];
            refTokenEntity.setRef_token(checkingRefToken);
            log.info("checkingRefToken = {}", checkingRefToken);
            RefTokenEntity checkedRefToken = iAdminMemberDaoMapper.selectRefToken(refTokenEntity);
            if (checkedRefToken != null) {
                int result = iAdminMemberDaoMapper.deleteDupRefToken(checkedRefToken);
                if (result > 0) {
                    log.info("중복 refToken 삭제 완료");
                    return "중복 refToken 삭제 완료";
                } else {
                    log.info("중복 refToken 삭제 실패");
                    return "중복 refToken 삭제 실패";
                }
            }

        }
        return "token 값이 잘못되었습니다.";
    }

    @Override
    public String signOut(HttpServletRequest request, HttpServletResponse response,AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity) {
        log.info("signOut");

//        final String authHeader = request.getHeader(HttpHeaders.COOKIE);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null) {
//            log.info("authHeaderNull");
//            return "refresh token is null";
//        }
//        String cookieToken = authHeader.substring(7);
//        refreshToken = cookieToken.split("=")[1];
//
//
//        // ref token에서 userEmail 추출
//        userEmail = jwtAuthenticationFilter.getUserEmail(secretKey, refreshToken);
//
//        // userEmail 이 동일한 사람 DB에서 삭제
//        log.info("userEmail = {}", userEmail);
//        AdminMemberDto.setEmail(userEmail);
//        int deleteMemberResult = iAdminMemberDaoMapper.deleteMember(userEmail);
//        if(deleteMemberResult <= 0){
//            log.info("delete Member fail");
//            return "회원탈퇴 실패";
//        }
//
//        refTokenEntity.setRef_token(refreshToken);
//        log.info("refreshToken = {}", refreshToken);
//        RefTokenEntity checkedRefToken = iAdminMemberDaoMapper.selectRefToken(refTokenEntity);
//        if (checkedRefToken != null) {
//            int result = iAdminMemberDaoMapper.deleteDupRefToken(checkedRefToken);
//            if (result > 0) {
//                log.info("중복 refToken 삭제 완료");
//            } else {
//                log.info("중복 refToken 삭제 실패");
//                return "중복 refToken 삭제 실패";
//            }
//        }
//
//        return "회원 탈퇴 성공";
//    }
//
//
        return null;
    }


}
