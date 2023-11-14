package com.btc.swimpyo.backend.service.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IUserMypageService {
    List<ReservationDto> GetRezList(HttpServletRequest request, int u_r_no);
}
