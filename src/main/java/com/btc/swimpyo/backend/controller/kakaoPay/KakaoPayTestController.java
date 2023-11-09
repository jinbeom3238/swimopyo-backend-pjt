//package com.btc.swimpyo.backend.controller.kakaoPay;
//
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
//import com.btc.swimpyo.backend.service.kakaoPay.BusinessLogicException;
//import com.btc.swimpyo.backend.service.kakaoPay.ExceptionCode;
//import com.btc.swimpyo.backend.service.kakaoPay.KakaoPayTestService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@Log4j2
//@RestController
//@RequestMapping("/api/payment")
//@RequiredArgsConstructor
//public class KakaoPayTestController {
//
//
//        private final KakaoPayTestService kakaoPayTestService;
//
//        /**
//         * 결제요청
//         */
//        @PostMapping("/ready")
//        public KakaoReadyResponseDto readyToKakaoPay() {
//
//            return kakaoPayTestService.kakaoPayReady();
//
//        }
//
//        /**
//         * 결제 성공
//         */
//        @GetMapping("/success")
////        public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {
//        public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken, @RequestParam("tid") String tid) {
//            log.info("afterPayRequest");
//            log.info("pgToken = {}", pgToken);
//            log.info("tid = {}", tid);
//
//            KakaoApproveResponseDto kakaoApprove = kakaoPayTestService.approveResponse(pgToken, tid);
//
//            return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
//
//        }
//
//        /**
//         * 결제 진행 중 취소
//         */
//        @GetMapping("/cancel")
//        public void cancel() {
//
//            throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
//
//        }
//
//        /**
//         * 결제 실패
//         */
//        @GetMapping("/fail")
//        public void fail() {
//
//            throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
//
//        }
//
//    /*
//     * 환불
//     */
//    @PostMapping("/refund")
//    public ResponseEntity refund( @RequestParam("tid") String tid) {
//
//        KakaoCancelResponseDto kakaoCancelResponse = kakaoPayTestService.kakaoCancel(tid);
//
//        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
//    }
//
//    }
//
