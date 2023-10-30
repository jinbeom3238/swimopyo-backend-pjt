package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminRoomService {

    // 등록
    public String registConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_image);
}
