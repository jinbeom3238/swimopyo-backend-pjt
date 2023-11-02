package com.btc.swimpyo.backend.service.member.user;

import com.btc.swimpyo.backend.config.Constant;
import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.mappers.member.user.IUserMemberDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import com.btc.swimpyo.backend.utils.jwt.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserMemberService implements IUserMemberService {

    @Value("${secret-key}")
    private String secretKey;

    private final IUserMemberDaoMapper iUserMemberDaoMapper;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public int signUp(Map<String, Object> msgMap, UserMemberDto userMemberDto) {
        log.info("signUp");
        log.info("msgMap => {}", msgMap);
        userMemberDto.setU_m_id(msgMap.get("id").toString());
        userMemberDto.setU_m_pw(passwordEncoder.encode(msgMap.get("pw").toString()));
        userMemberDto.setU_m_name(msgMap.get("name").toString());
        userMemberDto.setU_m_email(msgMap.get("email").toString());
        userMemberDto.setU_m_birth(msgMap.get("birth").toString());
        userMemberDto.setU_m_phone(msgMap.get("phone").toString());
        userMemberDto.setU_m_nickname(msgMap.get("nickname").toString());

        int result = Constant.USER_SIGNUP_FAIL;
        UserMemberDto idVerifiedUserMemberDto = iUserMemberDaoMapper.isMemberUser(userMemberDto);
        if (idVerifiedUserMemberDto != null) {
            return result;
        }

        result = iUserMemberDaoMapper.insertMemberUser(userMemberDto);
        if (result > 0) {
            log.info("SIGN UP SUCCESS");
            result = Constant.USER_SIGNUP_SUCCESS;
        } else {
            log.info("SIGN UP FAIL");
            result = Constant.USER_DUP_MEMBER;
        }
        return result;
    }

    @Override
    public Map<String, Object> signIn(Map<String, Object> msgMap,
                                      UserMemberDto userMemberDto,
                                      RefTokenEntity refTokenEntity,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        log.info("signIn");

        userMemberDto.setU_m_id(msgMap.get("id").toString());
        userMemberDto.setU_m_pw(msgMap.get("pw").toString());

        Map<String, Object> map = new HashMap<>();

        // 동일한 username 없음
        UserMemberDto idVerifiedUserMemberDto = iUserMemberDaoMapper.isMemberUser(userMemberDto);

        if (idVerifiedUserMemberDto != null && passwordEncoder.matches(userMemberDto.getU_m_pw(), idVerifiedUserMemberDto.getU_m_pw())) {
            /*
                로그인 시
                동일한 refresh token 명의 행이 있다면 delete 후
                새로 발급받은 refresh token을 insert해준다.
             */
            final String authHeader = request.getHeader(HttpHeaders.COOKIE);
            final String checkingRefToken;
            if (authHeader != null) {
                String cookieToken = authHeader.substring(7);
                checkingRefToken = cookieToken.split("=")[1];
                refTokenEntity.setRef_token(checkingRefToken);
                RefTokenEntity checkedRefToken = iUserMemberDaoMapper.selectRefToken(refTokenEntity);
                if (checkedRefToken != null) {
                    int result = iUserMemberDaoMapper.deleteDupRefToken(checkedRefToken);
                    if (result > 0) {
                        log.info("중복 refToken 삭제 완료");
                    } else {
                        log.info("중복 refToken 삭제 실패");
                    }
                }
            }

            List<GrantedAuthority> authorities = Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_USER")
            );

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userMemberDto.getU_m_id(), null, authorities);


            String accessToken = jwtProvider.createAccessToken(userMemberDto.getU_m_id(), secretKey, authenticationToken.getAuthorities());
            String refreshToken = jwtProvider.createRefreshToken(userMemberDto.getU_m_id(), secretKey);

            refTokenEntity.setRef_token(refreshToken);
            // refresh token -> tbl_tokens에 저장
            int result = iUserMemberDaoMapper.insertRefToken(refTokenEntity);
            if (result <= 0) {
                log.info("Ref Token 등록 실패");
            } else {
                log.info("Ref Token 등록 성공");
            }

            map.put("accessToken", accessToken);
            map.put("refreshToken", refreshToken);

        } else {
            map.put("result", "incorrectIdOrPw");

        }

        return map;
    }


}
