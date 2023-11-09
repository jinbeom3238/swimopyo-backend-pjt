package com.btc.swimpyo.backend.mappers.reservation.user;

import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

@Mapper
public interface IUserReservationDaoMapper {
    
    /*
     * 예약하기
     */
    // Room 정보 가져오기
    public ReservationDto selectRoomInfo(ReservationDto reservationDto);

    // 예약 정보 db에 저장
    public int insertRsvInfo(ReservationDto reservationDto);

    // 예약 가능한 방만 찾기
    public int searchDate(ReservationDto reservationDto);
    public int searchTime(ReservationDto reservationDto);
    public int insertRsvInfoByMoment(ReservationDto reservationDto);

    // [카카오페이] tid, next_redirect_pc_url, pg_token db에 저장
    public int insertKakaoPayReady(KakaoReadyResponseDto kakaoReady);

    // [카카오페이] 승인 시 받은 data 모두 저장
    public KakaoApproveResponseDto insertKakaoPayApprove(KakaoApproveResponseDto kakaoApprove);

    // [카카오페이] 승인 시 받은 결제 금액 정보 db 저장
    public AmountDto insertKakaoPayApproveAmount(KakaoApproveResponseDto kakaoApprove);

    /*
     * 환불
     */
    // 삭제할 예약 번호 받아오기
    public int selectRsvNoForDel(int deleteRsvNo);

    // db 삭제
    public int deleteRsvInfo(int u_r_no);
}
