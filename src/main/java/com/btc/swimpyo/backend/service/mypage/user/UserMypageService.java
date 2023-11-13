package com.btc.swimpyo.backend.service.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.mappers.mypage.user.IUserMypageDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserMypageService implements IUserMypageService{

    @Value("${secret-key}")
    private String secretKey;

    private final IUserMypageDaoMapper iUserMypageDaoMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    public List<ReservationDto> GetRezList(HttpServletRequest request) {
        log.info("GetRezList");

        String refreshToken = null;
        Cookie[] authHeader = request.getCookies();
        if (authHeader != null) {
            for (Cookie cookie : authHeader) {
                log.info("str => {}", cookie.getName());
                if ("authorization".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        final String userEmail;
        userEmail = jwtAuthenticationFilter.getUserEmail(secretKey, refreshToken);
        log.info("tp => {}", userEmail);

        return iUserMypageDaoMapper.selectRezList(userEmail);
    }
}
