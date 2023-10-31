package com.btc.swimpyo.backend.mappers.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminRoomDaoMapper {

    // 등록
    public int insertRoomInfo(AdminRoomDto adminRoomDto);   // 이미지를 제외한 룸 정보 등록

    public int selectRoomForArNo(AdminRoomDto adminRoomDto);   // a_r_no를 가져오기 위함

    public void insertRoomImage(AdminRoomImageDto adminRoomImageDto);   // Room 이미지 등록

    /*
     * 상세페이지 조회
     */
    // Room 정보 조회(이미지 제외)
    public AdminRoomDto selectRoomInfo(int a_m_no);
    // Room 이미지 조회
    public List<String> selectRoomImg(int a_r_no);

    // 수정

    // 삭제

}
