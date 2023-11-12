package com.btc.swimpyo.backend.dto.kakaoPay;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;


@ToString
@Data
public class KakaoReadyResponseDto  {

    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
    private String created_at;

    private String pg_token;
    private String partner_user_id;

    private String partner_order_id;
    private String approval_url;

    private String item_name;
    private String cid;

    private int total; // 총 결제 금액
    private int tax_free; // 비과세 금액
    private int tax; // 부가세 금액
    private int point; // 사용한 포인트
    private int discount; // 할인금액
    private int green_deposit; // 컵 보증금

}
