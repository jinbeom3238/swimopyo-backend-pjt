package com.btc.swimpyo.backend.dto.room.user;

import lombok.Data;

@Data
public class UserReviewDto {

    private int r_no;
    private String use_yn;
    private int a_r_no;
    private int u_m_no;
    private int p_am_no;
    private String r_title;
    private String r_content;
    private String r_image;
    private int r_sa_point;
    private String r_reg_date;
    private String r_mod_date;

}
