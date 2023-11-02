package com.btc.swimpyo.backend.controller.member.user;


import com.btc.swimpyo.backend.config.Constant;
import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.service.member.admin.IAdminMemberService;
import com.btc.swimpyo.backend.service.member.user.IUserMemberService;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/member")
@RequiredArgsConstructor
public class UserMemberController {

    private final IUserMemberService iUserMemberService;



    @PostMapping("/signUp")
    public String signUp(@RequestBody Map<String, Object> msgMap, UserMemberDto userMemberDto) {
        log.info("signUp");
        int response = iUserMemberService.signUp(msgMap, userMemberDto);
        log.info("response : {}", response);

        if(response == Constant.ADMIN_SIGNUP_SUCCESS){
            return "MemberUserSignUpSuccess";
        } else if(response == Constant.ADMIN_DUP_MEMBER){
            return "MemberUserDup";
        } else {
            return "MemberUserSignUpFail";
        }
    }

    @PostMapping("/signIn")
    public Object signIn(@RequestBody Map<String, Object> msgMap, UserMemberDto userMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response) {
        log.info("signIn");

        Map<String, Object> map = iUserMemberService.signIn(msgMap, userMemberDto, refTokenEntity, request,response);
        if (map != null) {
            if (map.get("result") == "incorrectIdOrPw") {
                return "IncorrectIdOrPw";
            }
            if (map.get("result") == "MemberUserNull") {
                return "MemberUserLoginNull";
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
            return "MemberUserLoginFail";
        }

    }

    @PostMapping("/hello")
    public ResponseEntity<String> hello(@RequestBody Map<String, Object> msgMap) {
        log.info("hello()");
        log.info("tp : {}", msgMap);
        return ResponseEntity.ok("user hello");

    }


}
