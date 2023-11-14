package com.btc.swimpyo.backend.mappers.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserMypageDaoMapper {
//    List<ReservationDto> selectRezList(String userEmail, int a_r_no);
    List<ReservationDto> selectRezList(Map<String, Object> map);
}
