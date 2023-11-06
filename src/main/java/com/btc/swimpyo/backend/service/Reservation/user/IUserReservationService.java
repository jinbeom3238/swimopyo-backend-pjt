package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;

public interface IUserReservationService {
    // 예약하기 버튼 클릭 시 화면(모달창)
    public ReservationDto createRsvReady(ReservationDto reservationDto);

    // 예약 및 결제 페이지
    public String createRsvApproval(ReservationDto reservationDto);
}
