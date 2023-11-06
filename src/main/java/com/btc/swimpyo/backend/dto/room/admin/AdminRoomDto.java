package com.btc.swimpyo.backend.dto.room.admin;

import lombok.Data;

import java.util.List;

@Data
public class AdminRoomDto {

    private int a_r_no;
    private String use_yn;
    private int a_acc_no;
    private int a_m_no;
    private String a_r_name;
    private String a_r_state;
    private int a_r_price;
    private String a_r_check_in;
    private String a_r_check_out;
    private String a_r_count;
    private String a_r_content;
    private String a_r_reg_date;
    private String a_r_mod_date;

    private int r_i_no;
    private List<Integer> r_i_nos;
    private String r_i_image;
    private List<String> r_i_images;

}
