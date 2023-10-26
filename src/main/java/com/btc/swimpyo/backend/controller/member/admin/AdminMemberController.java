package com.btc.swimpyo.backend.controller.member.admin;

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
@RequestMapping("/api/member/admin")
@RequiredArgsConstructor
public class AdminMemberController {

    private final IAdminMemberService iAdminMemberService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public String signUp(@RequestBody Map<String, Object> msgMap, AdminMemberDto adminMemberDto) {
        System.out.println("[AuthController] signUp");
        String response = iAdminMemberService.signUp(msgMap, adminMemberDto);
        return response;
//        return "hi";
    }

    @PostMapping("/signIn")
    public Object signIn(@RequestBody Map<String, Object> msgMap, AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[AuthController] signIn");

        Map<String, Object> map = iAdminMemberService.signIn(msgMap, adminMemberDto, refTokenEntity, request,response);

        if (map != null) {
            if (map.get("result") == HttpStatus.NOT_FOUND) {
                return "로그인 아이디가 없거나, 비밀번호를 틀리셨습니다.";
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
            return null;
        }

    }

    @PostMapping("/refreshToken")
    public Object refreshToken(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity) throws IOException {
        log.info("refreshToken in");

        Map<String, Object> map = iAdminMemberService.refreshToken(request, response, refTokenEntity);
        if(map.get("result") == "nullCheckRefToken"){

            return "이미 탈퇴한 회원 또는 로그아웃이 된 대상이므로 로그인 다시 해주세요.";
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
        if(result == "token 값이 잘못되었습니다."){
            log.info("token 값이 잘못되었습니다.");
            return "token 값이 잘못되었습니다.";
        }

        return "logout success";
    }

    @PostMapping("/sign_out")
    public Object signOut(HttpServletRequest request, HttpServletResponse response,AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity) {
        log.info("signOut");

        String result = iAdminMemberService.signOut(request, response, adminMemberDto, refTokenEntity);
        if(result == "중복 refToken 삭제 실패"){
            log.info("중복 refToken 삭제 실패");
            return "signOut fail";
        }
        if(result == "token 값이 잘못되었습니다."){
            log.info("token 값이 잘못되었습니다.");
            return "token 값이 잘못되었습니다.";
        }

        return "signOut success";
    }

}
