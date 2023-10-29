package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IAdminAccmService {

    // 등록
//    public void registConfirm(AdminAccmDto adminAccmDto);
//    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile a_acc_image);
//    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_acc_images);
//    public List<String> registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_acc_images);

    String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_acc_images);

    @Transactional
//    void createS3(String a_acc_image, MultipartFile file);
//    String createS3(MultipartFile file);
//    public void createS3(String a_acc_image, MultipartFile[] files);
//    public void createS3(MultipartFile[] files);
    //상세페이지 보기
//    public AdminAccmDto showAccmDetail(int a_acc_no);
    public Map<String, Object> showAccmDetail(int a_m_no);
    // 수정
//    public int modifyConfirm(AdminAccmDto adminAccmDto);
//    public int modifyConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_images);
    public String modifyConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_image);

    // 삭제
    public int deleteAccm(int a_m_no);
}
