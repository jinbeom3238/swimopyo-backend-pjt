package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;

public interface IAdminRoomService {

    // 등록
    public int registConfirm(int a_acc_no, AdminRoomDto adminRoomDto);
}
