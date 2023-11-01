package com.btc.swimpyo.backend.dto.room.admin;

import lombok.Data;

import java.util.List;

@Data
public class AdminRoomImageDto {

    private int r_i_no;
    private int a_r_no;
    private String r_i_image;
    private List<String> r_i_images;
    private String r_i_reg_date;

}
