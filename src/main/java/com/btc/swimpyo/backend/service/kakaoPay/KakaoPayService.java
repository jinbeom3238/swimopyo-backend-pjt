package com.btc.swimpyo.backend.service.kakaoPay;

import com.btc.swimpyo.backend.dto.kakaoPay.KakaoApproveResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoCancelResponseDto;
import com.btc.swimpyo.backend.dto.kakaoPay.KakaoReadyResponseDto;
import com.btc.swimpyo.backend.mappers.kakaoPay.IKakaoPayDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    private final IKakaoPayDaoMapper iKakaoPayDaoMapper;

    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "1980ead0ae347e6c0c29225e22ede43c"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요

    private KakaoReadyResponseDto kakaoReady;

    public KakaoReadyResponseDto kakaoPayReady() {
        log.info("[KakaoPayApiService] kakaoPayReady()");

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "90000");
        parameters.add("partner_user_id", "iieunji023");
        parameters.add("item_name", "제주 하얏트 호테루");
        parameters.add("quantity", "1");
        parameters.add("total_amount", "100000");
        parameters.add("vat_amount", "10000");
        parameters.add("tax_free_amount", "90000");
        parameters.add("approval_url", "http://localhost:8090/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8090/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8090/payment/fail"); // 실패 시 redirect url

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);

        return kakaoReady;

    }

    /*
     * 결제 완료 승인
     */
    public KakaoApproveResponseDto approveResponse(String pgToken) {
        log.info("[KakaoPayService] ApproveResponse()");

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
//        parameters.add("tid", kakaoReady.getTid());
        parameters.add("tid", "T547b40c2b8b1abb93e6");
        parameters.add("partner_order_id", "90000");
        parameters.add("partner_user_id", "iieunji023");
        parameters.add("pg_token", "078afb7df5107ad82338");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        return approveResponse;
    }


    /*
     * 결제 환불
     */
    public KakaoCancelResponseDto kakaoCancel() {

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", "T547b40c2b8b1abb93e6");
        parameters.add("cancel_amount", "100000");
        parameters.add("cancel_tax_free_amount", "90000");
        parameters.add("cancel_vat_amount", "10000");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoCancelResponseDto cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponseDto.class);

        return cancelResponse;
    }

    private MultiValueMap<String, String> getHeaders() {
        log.info("[KakaoPayApiService] getHeaders()");

        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;
        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;

    }

}
