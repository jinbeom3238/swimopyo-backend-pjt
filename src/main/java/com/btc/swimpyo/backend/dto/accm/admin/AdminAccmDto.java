package com.btc.swimpyo.backend.dto.accm.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminAccmDto {

    private int a_acc_no;
    private String use_yn;
    private String a_acc_name;
    private String a_acc_intro;
    private String a_acc_kind;
    private String a_acc_bn;
    private int a_m_no;
    private String a_acc_address;
    private String a_acc_phone;
//    private String a_acc_image;
    private String a_acc_reg_date;
    private String a_acc_mod_date;

    private String a_m_email;
    private String a_m_name;

    private String a_r_check_in;

    private String a_r_p_state;
    private String a_r_price;

    private  String a_i_image;

//    private List<String> a_acc_images; // 여러 장의 이미지 URL을 저장할 리스트

}
