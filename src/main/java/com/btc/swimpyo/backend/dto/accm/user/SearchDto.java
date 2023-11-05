package com.btc.swimpyo.backend.dto.accm.user;

import lombok.Data;

@Data
public class SearchDto {

    private String accm_name;
    private String room_name;
    private int total_room;
    private int remaining_room;
    private String reser_date;

}
