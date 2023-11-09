package com.btc.swimpyo.backend.dto.reservation;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class ReservationDto {

    private int u_r_no;
    private String use_yn;
    private String u_m_email;
    private int a_r_no;
    private String u_r_check_in;
    private String u_r_check_out;
    private Time u_r_check_in_time;
    private Time u_r_check_out_time;
    private String u_r_car_yn;
    private String u_r_stay_yn;
    private String u_r_name;
    private String u_r_phone;
    private int c_no;
    private int p_no;
    private String u_r_reg_date;

    private String a_r_state;
    private int a_r_price;
    private String a_r_name;
    private String a_r_check_in;
    private String a_r_check_out;
    private int a_acc_no;
    private String a_acc_name;

    // kakaopay ready
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
    private String created_at;
    private String pg_token;
    private String partner_order_id;
    private String approval_url;

}
