package com.btc.swimpyo.backend.service.room.user;

import java.util.Map;

public interface IUserRoomService {

    // 룸 리스트
    public Map<String,Object> showRoomList(int a_acc_no);
    // 룸 정보 조회
    public Map<String, Object> showRoomDetail(int a_r_no);


    // 예약

    // 리뷰

}
