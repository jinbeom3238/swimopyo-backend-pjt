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
    static final String admin_Key = "1980ead0ae347e6c0c29225e22ede43c";

    private KakaoReadyResponseDto kakaoReady;

    public KakaoReadyResponseDto kakaoPayReady() {
        log.info("[KakaoPayApiService] kakaoPayReady()");

        /*
         * <카카오페이 요청 양식>
         * parameters : 여러 개의 값이 하나의 키에 매핑되는 데이터를 저장하기 위한 자료구조
         * 문자열 키와 값의 쌍을 저장하는 Map 사용
         * 결제 요청에 필요한 파라미터들 추가
         */
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "90000");        // 가맹점 주문 번호
        parameters.add("partner_user_id", "iieunji023");    // 가맹점 회원 ID
        parameters.add("item_name", "제주 하얏트 호텔");       // 상품명
        parameters.add("quantity", "1");                    // 주문 수량
        parameters.add("total_amount", "100000");           // 총 금액
        parameters.add("vat_amount", "10000");              // 부가세
        parameters.add("tax_free_amount", "90000");         //상품 비과세 금액
        parameters.add("approval_url", "http://localhost:8090/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8090/payment/cancel");   // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8090/payment/fail");       // 실패 시 redirect url

        /*
         * HTTP 요청을 보내기 위한 엔터티
         * parameters와 이 요청에 대한 헤더 정보가 포함된다.
         */
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        /*
         * 외부에 보낼 url
         * HTTP 요청을 보내고 응답을 받기 위한 클래스
         */
        RestTemplate restTemplate = new RestTemplate();

        /*
         * POST 요청을 보내고,
         * 그에 대한 응답을 KakaoReadyResponseDto 클래스의 객체로 받아온다.
         */
        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);

        return kakaoReady;

    }

    /*
     * 결제 완료 승인
     * 카카오페이에서 결제 승인을 요청하고, 그에 대한 응답을 받아오는 역할
     */
    public KakaoApproveResponseDto approveResponse(String pgToken) {
        log.info("[KakaoPayService] ApproveResponse()");

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
//        parameters.add("tid", kakaoReady.getTid());
        parameters.add("tid", "T547b8052b8b1abb9406");
        parameters.add("partner_order_id", "90000");        // 가맹점 주문 번호
        parameters.add("partner_user_id", "iieunji023");    // 가맹점 회원 ID
        parameters.add("pg_token", "78a4648fc91f26ec0a2e"); // 승인 요청 -> 성공시 파라미터 값으로 받아와야 함

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
        parameters.add("tid", "T547b8052b8b1abb9406");      // 환불할 결제 고유 번호
        parameters.add("cancel_amount", "100000");          // 환불 금액
        parameters.add("cancel_tax_free_amount", "90000");  // 환불 비과세 금액
        parameters.add("cancel_vat_amount", "10000");       // 환불 부가세

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

    /*
     * 카카오 요구 헤더값
     */
    private MultiValueMap<String, String> getHeaders() {
        log.info("[KakaoPayApiService] getHeaders()");

        /*
         * HTTP 요청 헤더를 저장하기 위한 객체
         */
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;
        httpHeaders.set("Authorization", auth);

        /*
         * "Content-type" 헤더를 설정
         * 폼 데이터를 전송할 때 사용되는 MIME 타입인 "application/x-www-form-urlencoded;charset=utf-8"로 설정
         */
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;

    }

}
