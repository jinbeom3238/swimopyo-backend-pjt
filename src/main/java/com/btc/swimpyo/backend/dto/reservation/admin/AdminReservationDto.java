package com.btc.swimpyo.backend.dto.reservation.admin;

import lombok.Data;

@Data
public class AdminReservationDto {

    private int u_r_no;
    private String use_yn;
    private String u_m_email;
    private int a_r_no;
    private String u_r_check_in;
    private String u_r_check_out;
    private String u_r_car_yn;
    private String u_r_stay_yn;
    private String u_r_name;
    private String u_r_phone;
    private int c_no;
    private int p_no;
    private String u_r_reg_date;

    private String a_r_state;
    private String a_r_price;
    private String a_r_name;
    private String a_r_check_in;
    private String a_r_check_out;
    private int a_acc_no;
    private String a_acc_name;

}
