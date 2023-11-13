package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;

import java.util.Map;

public interface IUserReservationService {
    // 예약하기 버튼 클릭 시 화면(모달창)
    public ReservationDto createRsvReady(ReservationDto reservationDto);

    // 예약 및 결제 페이지
    public Map<String, Object> createRsvApproval(ReservationDto reservationDto);

    Map<String, Object> registRsv(String pg_token, String partner_order_id);

    // 환불
    public String refundRsv(KakaoApproveResponseDto kakaoApproveResponseDto, AmountDto amountDto, int deleteRsvNo);
}
