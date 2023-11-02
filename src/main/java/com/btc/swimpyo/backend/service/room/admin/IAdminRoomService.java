package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IAdminRoomService {

    // 등록
    public String registConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_image);

    // 상세페이지 조회
    public Map<String, Object> showRoomDetail(int a_m_no);

    // 수정
    public String modifyConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_image, List<Integer> deleteNos);

    // 삭제
    public int deleteConfirm(int a_m_no);
//    public int deleteConfirm(AdminRoomDto adminRoomDto);

    // Room 리스트 조회 - 숙박시설 상세 페이지에서 보여지는 부분
    public Map<String, Object> showRoomList(int a_acc_no);

}
