package com.btc.swimpyo.backend.mappers.reservation.user;

import com.btc.swimpyo.backend.dto.reservation.admin.AdminReservationDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserReservationDaoMapper {

    // Room 정보 가져오기
    public AdminReservationDto selectRoomInfo(AdminReservationDto adminReservationDto);

    // 예약 정보 db에 저장
    public int insertRsvInfo(AdminReservationDto adminReservationDto);

    // 모달창에서 입력받은 예약 날짜, 가격, 숙박/대실 여부 값 받아오기
    public int selectRoomModal(AdminReservationDto adminReservation);
    
}
