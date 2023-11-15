package com.btc.swimpyo.backend.mappers.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserMypageDaoMapper {
//    List<ReservationDto> selectRezList(String userEmail, int a_r_no);
    List<Map<String,Object>> selectRezList(Map<String, Object> map);

    List<UserReviewDto> selectRezAddressForList(int rNo);

    Map<String, Object> selectRezDetail(Map<String, Object> map);

//    List<UserReviewDto> selectReviewInfo(String userEmail);
    List<Map<String, Object>> selectReviewInfo(String userEmail);
}
