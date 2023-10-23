package com.btc.swimpyo.backend.mappers.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminAccmDaoMapper {

    // 등록
    public void insertAccmInfo(AdminAccmDto adminAccmDto);

    // 상세페이지 조회
    public AdminAccmDto selectAccmInfo(int a_m_no);

    // 수정
    public int updataeAccmInfo(AdminAccmDto adminAccmDto);

    // 삭제
    public int deleteAccmInfo(int a_m_no);

}
