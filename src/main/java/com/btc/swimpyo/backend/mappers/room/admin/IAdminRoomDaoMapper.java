package com.btc.swimpyo.backend.mappers.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminRoomDaoMapper {

    // 등록
    public int insertRoomInfo(AdminRoomDto adminRoomDto);   // 이미지를 제외한 룸 정보 등록

    public int selectRoomForArNo(AdminRoomDto adminRoomDto);   // a_r_no를 가져오기 위함

    public void insertRoomImage(AdminRoomImageDto adminRoomImageDto);   // Room 이미지 등록

    // 수정

    // 삭제

}
