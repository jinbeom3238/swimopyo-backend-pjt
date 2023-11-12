package com.btc.swimpyo.backend.controller.kakaoPay;

import com.btc.swimpyo.backend.dto.kakaoPay.AmountDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.service.kakaoPay.BusinessLogicException;
import com.btc.swimpyo.backend.service.kakaoPay.ExceptionCode;
import com.btc.swimpyo.backend.service.kakaoPay.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    /*
     * 결제 요청
     */
    @PostMapping("/ready")
    public KakaoReadyResponseDto readyToKakaoPay(@RequestBody ReservationDto reservationDto) {

        log.info("[KakaoPayController] readyToKakaoPay()");

        return kakaoPayService.kakaoPayReady(reservationDto);

    }


    /*
     * 결제 성공
     */
    @GetMapping("/success")
//    public ResponseEntity afterPayRequest(@RequestParam(value = "pg_token") String pgToken, @RequestPart KakaoReadyResponseDto kakaoReady) {
    public KakaoApproveResponseDto afterPayRequest(@RequestParam(value = "pg_token") String pgToken, KakaoReadyResponseDto kakaoReadyResponseDto) {

        log.info("[KakaoPayController] afterPayRequest()");

//        ReservationDto reservationDto = new ReservationDto();

        kakaoReadyResponseDto.setPg_token(pgToken);
        log.info("pgToken:" + pgToken);

        KakaoApproveResponseDto kakaoApprove = kakaoPayService.approveResponse(kakaoReadyResponseDto);

        log.info("[KakaoApproveResponseDto] :" + kakaoApprove);

//        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
        return kakaoApprove;

    }

    /*
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel() {

        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);

    }

    /*
     * 결제 실패
     */
    @GetMapping("/fail")
    public void fail() {

        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);

    }

    /*
     * 환불
     */
    @PostMapping("/refund")
    public KakaoCancelResponseDto refund(KakaoApproveResponseDto kakaoApproveResponseDto, AmountDto amountDto) {

        KakaoCancelResponseDto kakaoCancelResponse = kakaoPayService.kakaoCancel(kakaoApproveResponseDto, amountDto);

//        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
        return kakaoCancelResponse;

    }

}