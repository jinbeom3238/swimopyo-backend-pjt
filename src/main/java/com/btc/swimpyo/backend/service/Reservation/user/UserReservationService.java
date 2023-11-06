package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.controller.kakaoPay.KakaoPayController;
import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.mappers.reservation.user.IUserReservationDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserReservationService implements IUserReservationService{

    private final IUserReservationDaoMapper iUserReservationDaoMapper;
    private final KakaoPayController kakaoPayController;
    private KakaoReadyResponseDto kakaoReady;
    private KakaoApproveResponseDto kakaoApprove;
    private AmountDto amount;

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
            // front에서 받아온 u_r_check_in_time에 + 3시간 추가한 값을 u_r_check_out_time에 넣어주기
            Time u_r_check_out_time = new Time(u_r_check_in_time.getTime() + (4 * 60 * 60 * 1000));
            reservationDto.setU_r_check_out_time(u_r_check_out_time);
            log.info("u_r_check_out_time:" + reservationDto.getU_r_check_out_time());

            // 대실 - - 예약 차있는 방 찾기
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

    @Override
    public String createRsvApproval(ReservationDto reservationDto) {
        log.info("[UserReservationService] createRsvApproval()");

        String user_id = "iieunji023@gmail.com";
        reservationDto.setU_m_email(user_id);

        log.info("u_r_stay_yn:" + reservationDto.getU_r_stay_yn());

        // 결제
        KakaoReadyResponseDto kakaoReadyResponseDto = kakaoPayController.readyToKakaoPay(reservationDto);
        /*kakaoReadyResponseDto.setTid(reservationDto.getTid());
        kakaoReadyResponseDto.setNext_redirect_pc_url(reservationDto.getNext_redirect_pc_url());
        kakaoReadyResponseDto.setCreated_at(reservationDto.getCreated_at());
        kakaoReadyResponseDto.setPg_token(reservationDto.getPg_token());*/

        kakaoReadyResponseDto.setPartner_order_id(reservationDto.getPartner_order_id());

        log.info("getPartner_order_id: "+ reservationDto.getPartner_order_id());

        kakaoReadyResponseDto.setU_m_email(reservationDto.getU_m_email());

        log.info("kakaoReady:" + kakaoReadyResponseDto);
        log.info("email:" + kakaoReadyResponseDto.getU_m_email());

        String pgToken = kakaoReadyResponseDto.getPg_token();

        // db에 tid, next_redirect_pc_url, pg_token 값 저장
        int isReady = iUserReservationDaoMapper.insertKakaoPayReady(kakaoReadyResponseDto);

        // next_redirect_pc_url -> 결제 승인 시 이동되는 redirect page에 나오는 pg_token= 뒤에 값 뽑아주면 됨
        // (아마 프론트에서 받아야 할 것으로 예상됨)
        kakaoReadyResponseDto.getNext_redirect_pc_url();
        log.info("db에 저장된 redirect 값!!" + kakaoReadyResponseDto.getNext_redirect_pc_url());

        String approval_url = kakaoReadyResponseDto.getApproval_url();
        log.info("approval_url:" + approval_url);
        log.info("approval_url token:" + approval_url.substring(approval_url.length()-20));





        if(isReady > 0) {
            log.info("[insertKakaoPayReady] isReady!!");

            kakaoPayController.afterPayRequest(pgToken, kakaoReadyResponseDto);

        }

        // 도보/차량, 실사용자 정보와 기존에 받았던 예약날짜, 숙박/대실, 가격 정보 db에 저장하기
        if (reservationDto.getU_r_stay_yn().equals("Y")){
            int result = iUserReservationDaoMapper.insertRsvInfo(reservationDto);
            log.info("reservationDto:" + reservationDto);

            if(result > 0) {
                log.info("예약 완료");

                return "success";

            } else {
                log.info("예약 실패 ㅠ_ㅠ");

                return "fail";

            }

        } else if(reservationDto.getU_r_stay_yn().equals("N")) {
            int result = iUserReservationDaoMapper.insertRsvInfoByMoment(reservationDto);

            Time u_r_check_out_time = reservationDto.getU_r_check_out_time();
//        log.info("u_r_check_out_time:" + u_r_check_out_time);

            if(result > 0) {
                log.info("예약 완료");

                return "success";

            } else {
                log.info("예약 실패 ㅠ_ㅠ");

                return "fail";

            }

        }

        return "success";
    }

}