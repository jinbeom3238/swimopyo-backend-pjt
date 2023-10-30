package com.btc.swimpyo.backend.mappers.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminAccmDaoMapper {

    // 등록
    public int insertAccmInfo(AdminAccmDto adminAccmDto);
    public int selectAccmForAmNo(int a_m_no);
//    public int insertAccmImage(int a_acc_no, String imageUrl);
    public int insertAccmImage(AdminAccmImageDto adminAccmImageDto);


    // 상세페이지 조회
    public AdminAccmDto selectAccmInfo(int a_m_no);
    public List<String> selectAccmImg(int a_acc_no);

    // 수정
    public int updateAccmInfo(AdminAccmDto adminAccmDto);
    public int deleteAccmImg(int a_acc_no );  // 새로운 사진 추가 전 delete
    public int insertAccmAiImage(AdminAccmImageDto adminAccmImageDto);
    public void updateAccmImg(Map<String, Object> msgData); // 새로운 이미지 update
    public List<String> selectAccmImgForUpdate(int a_acc_no); // 기존 이미지 select
    public AdminAccmDto selectAccmInfoForUpdate(int a_acc_no);  // 수정된 숙박시설 정보 select




    // 삭제
    public int deleteAccmInfo(int a_m_no);



}
