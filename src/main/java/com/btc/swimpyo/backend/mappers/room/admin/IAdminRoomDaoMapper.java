package com.btc.swimpyo.backend.mappers.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminRoomDaoMapper {

    // 등록
    public int insertRoomInfo(AdminRoomDto adminRoomDto);

    // 수정

    // 삭제

}
