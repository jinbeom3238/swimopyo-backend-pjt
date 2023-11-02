package com.btc.swimpyo.backend.service.member.user;

import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface IUserMemberService {


    int signUp(Map<String, Object> msgMap, UserMemberDto userMemberDto);

    Map<String, Object> signIn(Map<String, Object> msgMap, UserMemberDto userMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response);

    int modify(Map<String, Object> msgMap, UserMemberDto userMemberDto);

    UserMemberDto userInfo(HttpServletRequest request, UserMemberDto userMemberDto);

    Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity);

    String changePw(Map<String, Object> msgMap, HttpServletRequest request, UserMemberDto userMemberDto);

    String logout(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity);

    String signOut(HttpServletRequest request, HttpServletResponse response, UserMemberDto userMemberDto, RefTokenEntity refTokenEntity);
}
