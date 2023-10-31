package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IAdminAccmService {

    // 등록
    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_acc_images);

    //상세페이지 보기
    public Map<String, Object> showAccmDetail(int a_m_no);
    // 수정
    public String modifyConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_image, List<Integer> deleteImgs);

    // 삭제
    public int deleteAccm(int a_m_no);

    
}
