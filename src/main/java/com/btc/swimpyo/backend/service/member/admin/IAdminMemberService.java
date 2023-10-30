package com.btc.swimpyo.backend.service.member.admin;

import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface IAdminMemberService {

    // super는 SignUpResponseDto에 대한 부모 타입도 같이 반환
    int signUp(Map<String, Object> msgMap, AdminMemberDto adminMemberDto);

    Map<String,Object> signIn(Map<String, Object> msgMap, AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> refreshToken(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity);

    String logout(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity);

    String signOut(HttpServletRequest request, HttpServletResponse response, AdminMemberDto adminMemberDto, RefTokenEntity refTokenEntity);


}
