package com.btc.swimpyo.backend.service.member.user;

import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.mappers.member.user.IUserMemberDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class UserMemberService implements IUserMemberService{

    @Autowired
    IUserMemberDaoMapper iUserMemberDaoMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String signUp(Map<String, Object> msgMap, UserMemberDto userMemberDto) {
        log.info("signUp");

        userMemberDto.setU_m_id(msgMap.get("id").toString());
        userMemberDto.setU_m_pw(passwordEncoder.encode(msgMap.get("pw").toString()));
        userMemberDto.setU_m_name(msgMap.get("name").toString());
        userMemberDto.setU_m_email(msgMap.get("mail").toString());
        userMemberDto.setU_m_phone(msgMap.get("phone").toString());
        userMemberDto.setU_m_nickname(msgMap.get("nickname").toString());


        int result = -1;
        result = iUserMemberDaoMapper.insertMemberUser(userMemberDto);
        if (result > 0) {
            System.out.println("USER SIGN UP SUCCESS");
            return "USER SIGN UP SUCCESS";
        } else {
            System.out.println("USER SIGN UP FAIL");
            return "USER SIGN UP FAIL";
        }
    }

    @Override
    public Map<String, Object> signIn(Map<String, Object> msgMap, UserMemberDto userMemberDto, RefTokenEntity refTokenEntity, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
