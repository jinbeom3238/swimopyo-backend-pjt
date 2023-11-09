//package com.btc.swimpyo.backend.service.kakaoPay;
//
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
//import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class KakaoPayTestService {
//
//    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
//    static final String admin_Key = "1980ead0ae347e6c0c29225e22ede43c"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
//    private KakaoReadyResponseDto kakaoReady;
//
//    public KakaoReadyResponseDto kakaoPayReady() {
//
//        // 카카오페이 요청 양식
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("cid", cid);
//        parameters.add("partner_order_id", "123456789");
//        parameters.add("partner_user_id", "user1");
//        parameters.add("item_name", "초코파이");
//        parameters.add("quantity", "1");
//        parameters.add("total_amount", "2200");
//        parameters.add("vat_amount", "200");
//        parameters.add("tax_free_amount", "0");
//        parameters.add("approval_url", "http://localhost:3000/payment/success"); // 성공 시 redirect url
//        parameters.add("cancel_url", "http://localhost:3000/payment/cancel"); // 취소 시 redirect url
//        parameters.add("fail_url", "http://localhost:3000/payment/fail"); // 실패 시 redirect url
//
//        // 파라미터, 헤더
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
//
//        // 외부에 보낼 url
//        RestTemplate restTemplate = new RestTemplate();
//
//        kakaoReady = restTemplate.postForObject(
//                "https://kapi.kakao.com/v1/payment/ready",
//                requestEntity,
//                KakaoReadyResponseDto.class);
//
//        return kakaoReady;
//    }
//
//    /**
//     * 결제 완료 승인
//     */
//    public KakaoApproveResponseDto approveResponse(String pgToken, String tid) {
//
//        // 카카오 요청
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("cid", cid);
//        parameters.add("tid", tid);
//        parameters.add("partner_order_id", "UU159754657489241asdasd");
//        parameters.add("partner_user_id", "user1");
//        parameters.add("pg_token", pgToken);
//
//        // 파라미터, 헤더
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
//
//        // 외부에 보낼 url
//        RestTemplate restTemplate = new RestTemplate();
//
//        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
//                "https://kapi.kakao.com/v1/payment/approve",
//                requestEntity,
//                KakaoApproveResponseDto.class);
//
//        return approveResponse;
//    }
//
//    /**
//     * 결제 환불
//     */
//    public KakaoCancelResponseDto kakaoCancel(String tid) {
//
//        // 카카오페이 요청
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        parameters.add("cid", cid);
//        parameters.add("tid", tid);
//        parameters.add("cancel_amount", "2200");
//        parameters.add("cancel_tax_free_amount", "0");
//        parameters.add("cancel_vat_amount", "200");
//
//        // 파라미터, 헤더
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
//
//        // 외부에 보낼 url
//        RestTemplate restTemplate = new RestTemplate();
//
//        KakaoCancelResponseDto cancelResponse = restTemplate.postForObject(
//                "https://kapi.kakao.com/v1/payment/cancel",
//                requestEntity,
//                KakaoCancelResponseDto.class);
//
//        return cancelResponse;
//    }
//
//    /**
//     * 카카오 요구 헤더값
//     */
//    private HttpHeaders getHeaders() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        String auth = "KakaoAK " + admin_Key;
//
//        httpHeaders.set("Authorization", auth);
//        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        return httpHeaders;
//
//    }
//
//}