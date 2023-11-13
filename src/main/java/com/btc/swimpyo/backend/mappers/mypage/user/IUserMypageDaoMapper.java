package com.btc.swimpyo.backend.mappers.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMypageDaoMapper {
    List<ReservationDto> selectRezList(String userEmail);
}
