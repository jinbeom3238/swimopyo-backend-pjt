package com.btc.swimpyo.backend.controller.member.admin;

import com.btc.swimpyo.backend.config.Constant;
import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.service.member.admin.IAdminMemberService;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/admin/member")
@RequiredArgsConstructor
public class AdminMemberController {

    private final IAdminMemberService iAdminMemberService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody Map<String, Object> msgMap, AdminMemberDto adminMemberDto) {
        log.info("signUp");
        int response = iAdminMemberService.signUp(msgMap, adminMemberDto);
        log.info("response : {}", response);

        if(response == Constant.ADMIN_SIGNUP_SUCCESS){
            return "MemberAdminSignUpSuccess";
        } else if(response == Constant.ADMIN_DUP_MEMBER){
            return "MemberAdminDup";
        } else {
            return "MemberAdminSignUpFail";
        }
    }

    @PostMapping("/signIn")
    public Object signIn(@RequestBody Map<String, Object> msgMap, AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response) {
        log.info("signIn");

        Map<String, Object> map = iAdminMemberService.signIn(msgMap, adminMemberDto, refTokenEntity, request,response);
        log.info("map ==> {}", map);
        if (map != null) {
            if (map.get("result") == "incorrectIdOrPw") {
                return "IncorrectIdOrPw";
            }
            if (map.get("result") == "MemberAdminNull") {
                return "MemberAdminLoginNull";
            }

            // 로그인 성공했기때문에 accessToken, refreshToken 발급
            String refreshTokenValue = map.get("refreshToken").toString();
            log.info("refreshTokenValue");
            Cookie cookie = new Cookie("authorization", refreshTokenValue);
            cookie.setHttpOnly(true);  //httponly 옵션 설정
            cookie.setSecure(true); //https 옵션 설정
            cookie.setMaxAge(24 * 3600);
            cookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
            response.addCookie(cookie);

            return map.get("accessToken");

        } else {
            return "MemberAdminLoginFail";
        }

    }

    @PostMapping("/modify")
    public Object modify(@RequestBody Map<String, Object> msgMap, AdminMemberDto adminMemberDto){
        log.info("modify");

        int result = -1;
        result = iAdminMemberService.modify(msgMap, adminMemberDto);
        if(result == 0){
            return "MemberAdminModifyFail";
        } else if (result == 1) {
            return "MemberAdminModifySuccess";
        }

        return result;
    }

    @PostMapping("/refreshToken")
    public Object refreshToken(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity) {
        log.info("refreshToken in");

        Map<String, Object> map = iAdminMemberService.refreshToken(request, response, refTokenEntity);
        if(map.get("result") == "RefTokenNull"){

            return "RefTokenNullInCookie";
        }
        if(map.get("result") == "RefTokenNullInDB"){

            return "RefTokenNullInDB";
        }
        if(map.get("result") == "RefTokenExpired"){

            return "RefTokenExpired";
        }

        String refreshTokenValue = (String) map.get("ReRefreshToken");
        log.info("ReRefreshToken");
        Cookie cookie = new Cookie("authorization", refreshTokenValue);
        cookie.setHttpOnly(true);  //httponly 옵션 설정
        cookie.setSecure(true); //https 옵션 설정
        cookie.setMaxAge(24 * 3600);
        cookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
        response.addCookie(cookie);

        return map.get("ReAccessToken");

    }

    @PostMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity) {
        log.info("logout");

        String result = iAdminMemberService.logout(request, response, refTokenEntity);
        if(result == "중복 refToken 삭제 실패"){
            log.info("중복 refToken 삭제 실패");
            return "logout fail";
        }
        if(result == "token 값이 잘못 되었습니다."){
            log.info("token 값이 잘못 되었습니다.");
            return "token 값이 잘못 되었습니다.";
        }

        // Cookie 삭제
        Cookie myCookie = new Cookie("authorization", null);
        myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        myCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(myCookie);

        return "logout success";
    }

    @PostMapping("/signout")
    public Object signOut(HttpServletRequest request,
                          HttpServletResponse response,
                          AdminMemberDto adminMemberDto,
                          RefTokenEntity refTokenEntity) {
        log.info("signOut");

        String result = iAdminMemberService.signOut(request, response, adminMemberDto, refTokenEntity);
        if(result == "중복 refToken 삭제 실패"){
            log.info("중복 refToken 삭제 실패");
            return "signOutFail";
        }
        if(result == "refresh token is null"){
            log.info("refresh token is null");
            return "RefTokenNullInCookie";
        }

        // Cookie 삭제
        Cookie myCookie = new Cookie("authorization", null);
        myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        myCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
        response.addCookie(myCookie);

        return "signOutSuccess";
    }

    @PostMapping("/adminInfo")
    public Object adminInfo(HttpServletRequest request,AdminMemberDto adminMemberDto){
        log.info("adminInfo");

        AdminMemberDto adminInfo = iAdminMemberService.adminInfo(request, adminMemberDto);
        if(adminInfo == null){
            return null;
        }
        return adminInfo;
    }

    @PostMapping("/changePw")
    public Object changePw(@RequestBody Map<String, Object> msgMap,HttpServletRequest request,AdminMemberDto adminMemberDto){
        log.info("changePw");

        String result = iAdminMemberService.changePw(msgMap, request, adminMemberDto);

        if(result == "AdminChangePwSuccess") {
            return "AdminChangePwSuccess";
        }
        if(result == "AdminChangePwFail"){
            return "AdminChangePwFail";
        }

        return "AdminChangePwFail";
    }

}
