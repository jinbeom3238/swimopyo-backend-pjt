package com.btc.swimpyo.backend.service.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.mappers.mypage.user.IUserMypageDaoMapper;
import com.btc.swimpyo.backend.mappers.review.IUserReviewDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserMypageService implements IUserMypageService {

    @Value("${secret-key}")
    private String secretKey;

    private final IUserMypageDaoMapper iUserMypageDaoMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final IUserReviewDaoMapper iUserReviewDaoMapper;

    public UserMypageService(IUserMypageDaoMapper iUserMypageDaoMapper, JwtAuthenticationFilter jwtAuthenticationFilter, IUserReviewDaoMapper iUserReviewDaoMapper) {
        this.iUserMypageDaoMapper = iUserMypageDaoMapper;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.iUserReviewDaoMapper = iUserReviewDaoMapper;
    }

    @Override
    public List<ReservationDto> GetRezList(HttpServletRequest request, int u_r_no, int period) {
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
    public Map<String, Object> showReviewList(HttpServletRequest request) {
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

        Map<String, Object> msgData = new HashMap<>();
        List<UserReviewDto> r_ri_images = new ArrayList<>();
        List<UserReviewDto> r_xy_address = new ArrayList<>();
        List<UserReviewDto> reviewList = new ArrayList<>();

        int r_no;

        // 리뷰 리스트 가져오기(이미지 제외)
        List<UserReviewDto> userReviewDto = iUserReviewDaoMapper.selectReviewInfo(userEmail);
        log.info("userReviewDtos: " + userReviewDto);

        // 사용자가 작성한 리뷰 번호 가져오기
        List<Integer> r_nos = userReviewDto.stream()
                .map(UserReviewDto::getR_no)
                .toList();
        log.info("r_nos: " + r_nos);

        // r_no에 대한 이미지, 주소 정보 들고 오기
        for(int i = 0; i<r_nos.size(); i++) {

            r_no = r_nos.get(i);



            // 주소 정보 가져오기
//            r_xy_address = iUserMypageDaoMapper.selectRezAddressForList(r_no);

            // 이미지 정보 가져오기
//            r_ri_images = iUserReviewDaoMapper.selectReviewImgForList(r_no);

//            reviewList = iUserMypageDaoMapper.selectReviewList(r_no);

        }

        msgData.put("r_ri_images", r_ri_images);
        msgData.put("r_xy_address", r_xy_address);

        log.info(msgData);

        return msgData;

    }
}
