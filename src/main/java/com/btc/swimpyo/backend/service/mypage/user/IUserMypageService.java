package com.btc.swimpyo.backend.service.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface IUserMypageService {
    List<ReservationDto> GetRezList(HttpServletRequest request, int u_r_no, int period);

    Map<String, Object> showReviewList(HttpServletRequest request);
}
