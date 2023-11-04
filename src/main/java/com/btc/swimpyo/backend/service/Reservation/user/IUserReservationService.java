package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.dto.reservation.admin.AdminReservationDto;

public interface IUserReservationService {
    // 예약하기 버튼 클릭 시 화면(모달창)
    public String createReservation(AdminReservationDto adminReservationDto);
}
