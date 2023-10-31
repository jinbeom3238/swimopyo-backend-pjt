package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IAdminRoomService {

    // 등록
    public String registConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_image);

    // 상세페이지 보기
    public Map<String, Object> showRoomDetail(int a_m_no);
}
