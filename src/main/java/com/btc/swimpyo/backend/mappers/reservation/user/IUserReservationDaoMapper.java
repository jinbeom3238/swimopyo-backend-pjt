package com.btc.swimpyo.backend.mappers.reservation.user;

import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserReservationDaoMapper {

    /*
     * 예약하기
     */
    // Room 정보 가져오기
    public ReservationDto selectRoomInfo(ReservationDto reservationDto);
    // 예약 정보 db에 저장(숙박, 대실)
    public int insertRsvInfo(ReservationDto reservationDto);
    public int insertRsvInfoByMoment(ReservationDto reservationDto);
    // 예약 가능한 방만 찾기(숙박/대실)
    public int searchDate(ReservationDto reservationDto);
    public int searchTime(ReservationDto reservationDto);

    // [카카오페이] tid, next_redirect_pc_url, amount 값 등 db에 저장
    public int insertKakaoPayReady(KakaoReadyResponseDto kakaoReady);
    public int insertAmount(KakaoReadyResponseDto kakaoReadyResponseDto);
    // 예약번호(u_r_no) 가져오기
    public int selectRsvNo(ReservationDto reservationDto);
    // tid 값 tbl_user_reservation에 저장하기
    public void updateRsvTid(ReservationDto reservationDto);
    // [카카오페이] 승인 시 받은 data 모두 저장
    public int insertKakaoPayApprove(KakaoApproveResponseDto kakaoApprove);
    // [카카오페이] db에서 kakaopay_ready 값 가져오기
    public KakaoReadyResponseDto selectKakaoReadyInfo(String partner_order_id);
    // 결제 완료시 pay_yn 값 바꾸기
    public void updateRsvpayYN(ReservationDto reservationDto);
    // [카카오페이]  pg_token db에 저장
    public String success();

    /*
     * 환불
     */
    // 삭제할 예약 번호 받아오기
    public ReservationDto selectRsvNoForDel(int deleteRsvNo);
    // amount 정보 가져오기
    public AmountDto selectAmount(KakaoApproveResponseDto kakaoApproveResponseDto);
    // db 삭제
    public int deleteRsvInfo(ReservationDto reservationDto);
    // 결제 정보 삭제
    public int deletePayReady(KakaoApproveResponseDto kakaoApproveResponseDto);
    public int deletePayApproval(KakaoApproveResponseDto kakaoApproveResponseDto);
    public int deletePayAmount(KakaoApproveResponseDto kakaoApproveResponseDto);




}
