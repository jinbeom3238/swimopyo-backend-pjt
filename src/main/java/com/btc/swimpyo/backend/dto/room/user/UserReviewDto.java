package com.btc.swimpyo.backend.dto.room.user;

import lombok.Data;

@Data
public class UserReviewDto {

    private int r_no;
    private String use_yn;
    private int a_r_no;
    private String u_m_email;
    private int p_am_no;
    private String r_title;
    private String r_content;
    private int r_sa_point;
    private String r_reg_date;
    private String r_mod_date;
    private int isWrite;

    // image 테이블
    private int u_ri_no;
    private String r_ri_image;
    private String r_ri_reg_date;
    private String r_ri_mod_date;

    // 경도,위도 테이블
    private int r_xy_no;
    private String r_xy_address;
    private String r_xy_comment;
    private String r_xy_longitude;
    private String r_xy_latitude;
    private String r_xy_reg_date;
//    private String address;
  
    // 룸 예약번호
    private String a_r_name;
    private int u_r_no;

    // 숙박업소 테이블
    private int a_acc_no;
    private String a_acc_name;
    private String u_m_nickname;
    private String a_acc_address;

}
