package com.btc.swimpyo.backend.dto.accm.admin;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminAccmImageDto {

    private int a_i_no;
    private int a_acc_no;
    private String a_i_image;
    private String a_i_reg_date;
    private String a_i_mod_date;

}
