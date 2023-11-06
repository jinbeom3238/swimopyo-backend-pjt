package com.btc.swimpyo.backend.dto.kakaoPay;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
@Data
public class KakaoReadyResponseDto  {

    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
    private String created_at;

    private String pg_token;
    private String u_m_email;

    private String partner_order_id;
    private String approval_url;

}
