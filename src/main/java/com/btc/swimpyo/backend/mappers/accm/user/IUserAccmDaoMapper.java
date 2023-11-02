package com.btc.swimpyo.backend.mappers.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserAccmDaoMapper {

    /*
     * 리스트 조회
     */
    // 이미지 제외한 정보
    public List<AdminAccmDto> selectAccmList(AdminAccmDto adminAccmDto);
    // a_acc_no 값 조회
    public List<Integer> selectAccmNo(AdminAccmDto adminAccmDto);
    // 이미지 조회
    public List<String> selectAccmImgList(int a_acc_no);

    // 상세페이지 조회
    public AdminAccmDto selectAccmDetail(int a_acc_no);



}
