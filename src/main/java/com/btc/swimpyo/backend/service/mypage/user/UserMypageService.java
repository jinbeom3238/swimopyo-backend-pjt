package com.btc.swimpyo.backend.service.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.mappers.mypage.user.IUserMypageDaoMapper;
import com.btc.swimpyo.backend.mappers.review.IUserReviewDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserMypageService implements IUserMypageService {

    @Value("${secret-key}")
    private String secretKey;

    private final IUserMypageDaoMapper iUserMypageDaoMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final IUserReviewDaoMapper iUserReviewDaoMapper;


    @Override
    public List<Map<String,Object>> GetRezList(HttpServletRequest request, int u_r_no, int period) {
        log.info("GetRezList");
        Map<String, Object> map = new HashMap<>();

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

        map.put("userEmail", userEmail);
        map.put("u_r_no", u_r_no);
        map.put("period", period);


        return iUserMypageDaoMapper.selectRezList(map);
    }

    @Override
    public List<Map<String,Object>> showReviewList(HttpServletRequest request) {
        log.info("showReviewList");

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

        List<Map<String, Object>> tempList = iUserMypageDaoMapper.selectReviewInfo(userEmail);

        return tempList;

    }

    @Override
    public Map<String, Object> GetRezDetail(HttpServletRequest request, int u_r_no) {
        log.info("GetRezDetail");

        Map<String, Object> map = new HashMap<>();

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


        map.put("userEmail", userEmail);
        map.put("u_r_no", u_r_no);

        map = iUserMypageDaoMapper.selectRezDetail(map);

        // 날짜 파싱 및 일수 계산
        LocalDate startDay = LocalDate.parse(map.get("u_r_check_in").toString());
        LocalDate endDay = LocalDate.parse(map.get("u_r_check_out").toString());
        int days = (int) ChronoUnit.DAYS.between(startDay, endDay);

        log.info("days = {}", days);
        if(days > 1){
            map.put("a_r_pride", Integer.parseInt(map.get("a_r_pride").toString()) * days);
        }

        return map;

    }
}
