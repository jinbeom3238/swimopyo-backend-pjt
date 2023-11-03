package com.btc.swimpyo.backend.mappers.room.user;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.user.UserRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserRoomDaoMapper {
    /*
     * 리스트 조회
     */
    // Room 정보(이미지 제외)
    public List<AdminRoomDto> selectRoomInfoForList(int a_acc_no);
    // Room 이미지
    public List<String> selectRoomImgForList(int a_r_no);

    /*
     * 상세 페이지 조회 
     */
    // 룸 정보 조회(이미지 제외)
    public AdminRoomDto selectRoomInfo(int a_r_no);

    // r_i_no 값
    public List<Integer> selectRoomImgNo(int aRNo);

    // 이미지 정보
    public List<String> selectRoomImg(int rINo);

    // 예약

    // 리뷰
}
