package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;

import java.util.Map;

public interface IUserReservationService {
    // 예약하기 버튼 클릭 시 화면(모달창)
    public Map<String, Object> createRsvReady(ReservationDto reservationDto);

    // 예약 및 결제
    public Map<String, Object> createRsvApproval(ReservationDto reservationDto);

    // 결제 승인
    Map<String, Object> registRsv(String pg_token, String partner_order_id);
    // [카카오페이]front에 success 메세지 보내기 위함
//    public String success();

    // 환불
    public String refundRsv(KakaoApproveResponseDto kakaoApproveResponseDto, AmountDto amountDto, int deleteRsvNo);


}
