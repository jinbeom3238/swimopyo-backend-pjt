package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.controller.kakaoPay.KakaoPayController;
import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.mappers.reservation.user.IUserReservationDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserReservationService implements IUserReservationService{

    private final IUserReservationDaoMapper iUserReservationDaoMapper;
    private final KakaoPayController kakaoPayController;

    // 예약하기 버튼 클릭 시 화면(모달창)
    @Override
    public ReservationDto createRsvReady(ReservationDto reservationDto) {
        log.info("[UserReservationService] createRsvReady()");

        log.info("[createRsvReady] reservationDto" + reservationDto);

        // SecurityContextHolder : 전역적으로 값을 저장하기 위함
        // getAuthentication : 이 안에 user 정보를 저장함
        // getPrincipal : user id 값이 저장되어 있음
        /*  User user_info = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_id = user_info.getUsername(); */

        String user_id = "iieunji023@gmail.com";
        log.info("state:" + reservationDto.getU_r_stay_yn());

        // 숙박/대실 분류
        if(reservationDto.getU_r_stay_yn().equals("Y")) {
            log.info("stay_yn = Y !! ");
            // 숙박 - 예약 차있는 방 찾기
            int result = iUserReservationDaoMapper.searchDate(reservationDto);

            // 예약 불가시
            if(result > 0) {
                log.info("예약이 불가합니다.");

                return null;

            }
        } else if(reservationDto.getU_r_stay_yn().equals("N")){
            log.info("stay_yn = N !! ");

            Time u_r_check_in_time = reservationDto.getU_r_check_in_time();
            // front에서 받아온 u_r_check_in_time에 + 4시간 추가한 값을 u_r_check_out_time에 넣어주기
            Time u_r_check_out_time = new Time(u_r_check_in_time.getTime() + (4 * 60 * 60 * 1000));
            reservationDto.setU_r_check_out_time(u_r_check_out_time);
            log.info("u_r_check_out_time:" + reservationDto.getU_r_check_out_time());

            // 대실 - 예약 차있는 방 찾기
            int result = iUserReservationDaoMapper.searchTime(reservationDto);

            // 예약 불가시
            if(result > 0) {
                log.info("예약 불가능");

                return null;

            }

        }

        /*
         * 상세 Room 정보 가져오기
         * 여기서 rsvRoomInfo : 상세 방 정보
         * reservationDto : 모달창에서 받은 데이터, 가격 / 숙박 / 예약 날짜
         */
        ReservationDto rsvRoomInfo = iUserReservationDaoMapper.selectRoomInfo(reservationDto);
        log.info("adminReservation" + rsvRoomInfo);

        rsvRoomInfo.setU_m_email(user_id);
        rsvRoomInfo.getA_r_name();
        rsvRoomInfo.setU_r_check_in(reservationDto.getU_r_check_in());
        rsvRoomInfo.setU_r_check_out(reservationDto.getU_r_check_out());
        rsvRoomInfo.setU_r_stay_yn(reservationDto.getU_r_stay_yn());
        rsvRoomInfo.setA_r_price(reservationDto.getA_r_price());
        rsvRoomInfo.setU_r_check_in_time(reservationDto.getU_r_check_in_time());
        rsvRoomInfo.setU_r_check_out_time(reservationDto.getU_r_check_out_time());

        log.info("adminReservation" + rsvRoomInfo);
        log.info("a_r_name:" + rsvRoomInfo.getA_r_name());

        return rsvRoomInfo;

    }

    /* 예약 & 결제 페이지 */
    @Override
    public Map<String, Object> createRsvApproval(ReservationDto reservationDto) {
        log.info("[UserReservationService] createRsvApproval()");

        Map<String, Object> msgData = new HashMap<>();

//        String user_id = "iieunji023@gmail.com";
//        reservationDto.setU_m_email(user_id);

        log.info("u_r_stay_yn:" + reservationDto.getU_r_stay_yn());
        log.info("reservationDto: " + reservationDto);

        // 도보/차량, 실사용자 정보와 기존에 받았던 예약날짜, 숙박/대실, 가격 정보 db에 저장하기
        if (reservationDto.getU_r_stay_yn().equals("Y")){

            int result = iUserReservationDaoMapper.insertRsvInfo(reservationDto);
            log.info("reservationDto:" + reservationDto);

            if(result > 0) {
                log.info("예약 완료");

            } else {
                log.info("예약 실패 ㅠ_ㅠ");

            }

        } else if(reservationDto.getU_r_stay_yn().equals("N")) {

            int result = iUserReservationDaoMapper.insertRsvInfoByMoment(reservationDto);

            Time u_r_check_out_time = reservationDto.getU_r_check_out_time();

            if(result > 0) {
                log.info("예약 완료");

            } else {
                log.info("예약 실패 ㅠ_ㅠ");

            }

        }

        log.info("msgData : " + msgData);

        // 결제
        KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayController.readyToKakaoPay(reservationDto);

        kakaoReadyResponseDto.setPartner_order_id(reservationDto.getPartner_order_id());
        kakaoReadyResponseDto.setPartner_user_id(reservationDto.getU_m_email());

        log.info("getPartner_order_id: " + reservationDto.getPartner_order_id());
        log.info("kakaoReady:" + kakaoReadyResponseDto);
        log.info("email:" + kakaoReadyResponseDto.getPartner_user_id());

        // [카카오페이] db에 tid, next_redirect_pc_url 값 등을 저장
        int isReady = iUserReservationDaoMapper.insertKakaoPayReady(kakaoReadyResponseDto);
        int isAmount = iUserReservationDaoMapper.insertAmount(kakaoReadyResponseDto);

        reservationDto.setU_m_email(kakaoReadyResponseDto.getPartner_user_id());

        int u_r_no = iUserReservationDaoMapper.selectRsvNo(reservationDto);
        reservationDto.setU_r_no(u_r_no);
        reservationDto.setTid(kakaoReadyResponseDto.getTid());

        log.info("u_r_no: " + reservationDto.getU_r_no());

        // tid 값 tbl_user_reservation에 넣어주기
        iUserReservationDaoMapper.updateRsvTid(reservationDto);

        if (kakaoReadyResponseDto.getNext_redirect_pc_url() != null) {
            log.info("Next_redirect_pc_url:" + kakaoReadyResponseDto.getNext_redirect_pc_url());

            msgData.put("status", "success");
            msgData.put("kakaoReadyResponseDto", kakaoReadyResponseDto);
            msgData.put("reservationDto", reservationDto);

            return msgData;

        }

        msgData.put("status", "fail");

        return msgData;

    }

    @Override
    public Map<String, Object> registRsv(String pg_token) {
        log.info("[userReservationService] registRsv()");

        log.info("token: " + pg_token);

        ReservationDto reservationDto = new ReservationDto();

        Map<String, Object> msgData = new HashMap<>();

//        User user_info = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String u_m_email = user_info.getUsername();
        String u_m_email = "user1@gmail.com";

        // db에 저장된 kakaoReady 값들 가져오기
        KakaoReadyResponseDto kakaoReadyResponseDto = iUserReservationDaoMapper.selectKakaoReadyInfo(u_m_email);
        kakaoReadyResponseDto.setPg_token(pg_token);
        log.info("reservationDto:" + kakaoReadyResponseDto);

        // pg_token을 들고 kakaoAprove 가기
        KakaoApproveResponseDto kakaoApprove= kakaoPayController.afterPayRequest(pg_token, kakaoReadyResponseDto);

        kakaoApprove.setTotal(kakaoReadyResponseDto.getTotal());
        kakaoApprove.setTax(kakaoReadyResponseDto.getTax());
        kakaoApprove.setTax_free(kakaoReadyResponseDto.getTax_free());

        log.info("[kakaoApprove] " + kakaoApprove);

        log.info("[kakaoApprove] SUCCESS!!");

        reservationDto.setU_m_email(kakaoApprove.getPartner_user_id());

        if(kakaoApprove != null) {
            int isApprove = iUserReservationDaoMapper.insertKakaoPayApprove(kakaoApprove);

            log.info("isApprove : " + isApprove);

            int u_r_no = iUserReservationDaoMapper.selectRsvNo(reservationDto);
            reservationDto.setU_r_no(u_r_no);
            log.info("u_r_no: " + reservationDto.getU_r_no());
            log.info("u_m_email: " + reservationDto.getU_m_email());

            // 결제 완료시 예약 테이블 pay_yn 값 변경해주기
            iUserReservationDaoMapper.updateRsvpayYN(reservationDto);

            msgData.put("status", "success");
            msgData.put("kakaoReadyResponseDto", kakaoReadyResponseDto);
            msgData.put("kakaoApprove", kakaoApprove);
            msgData.put("reservationDto", reservationDto);

            return msgData;

        }

        log.info("[kakaoApprove] FAIL!!");



        msgData.put("status", "fail");
        return msgData;

    }

    @Override
    public String refundRsv(KakaoApproveResponseDto kakaoApproveResponseDto, AmountDto amountDto, int deleteRsvNo) {
        log.info("[UserReservationService] refundRsv()");

        // 환불 받을 예약 번호를 통해 예약번호와 이메일 가져오기
        ReservationDto reservationDto = iUserReservationDaoMapper.selectRsvNoForDel(deleteRsvNo);
        log.info("reservationDto: " + reservationDto);


        if(reservationDto != null) {
            log.info("DELETE RSVNO EXIST!!");

            // amount 정보 가져오기
            amountDto = iUserReservationDaoMapper.selectAmount(kakaoApproveResponseDto);
            // cid 가져오기
//            String cid = iUserReservationDaoMapper.selectCid(kakaoApproveResponseDto);
//            kakaoApproveResponseDto.setCid(cid);

            log.info("kakaoApproveResponseDto:" +kakaoApproveResponseDto);
            log.info("amountDto:" +amountDto);


            // 카카오페이 환불
            KakaoCancelResponseDto kakaoCancelResponseDto = kakaoPayController.refund(kakaoApproveResponseDto, amountDto);

            // 환불이 된다면
            if(kakaoCancelResponseDto != null) {
                log.info("카카오 환불 완료");

                // db에서 삭제
                int deleteRsv = iUserReservationDaoMapper.deleteRsvInfo(reservationDto);
                int deletePay = iUserReservationDaoMapper.deletePayReady(kakaoApproveResponseDto);
                int deleteApproval = iUserReservationDaoMapper.deletePayApproval(kakaoApproveResponseDto);
                int deleteAmount = iUserReservationDaoMapper.deletePayAmount(kakaoApproveResponseDto);

                if(deleteRsv > 0 && deletePay > 0 && deleteApproval > 0 && deleteAmount > 0) {
                    log.info("예약 취소 완료");

                    return "success";

                } else {
                    log.info("예약 취소 실패");

                    return "fail";

                }

            }

        }
        return "successs";
    }



}