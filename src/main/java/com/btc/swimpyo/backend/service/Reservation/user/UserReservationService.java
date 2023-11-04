package com.btc.swimpyo.backend.service.Reservation.user;

import com.btc.swimpyo.backend.dto.reservation.admin.AdminReservationDto;
import com.btc.swimpyo.backend.mappers.reservation.user.IUserReservationDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserReservationService implements IUserReservationService{

    private final IUserReservationDaoMapper iUserReservationDaoMapper;

    // 예약하기 버튼 클릭 시 화면(모달창)
    @Override
    public String createReservation(AdminReservationDto adminReservationDto) {
        log.info("[UserReservationService] createReservation()");

        // SecurityContextHolder : 전역적으로 값을 저장하기 위함
        // getAuthentication : 이 안에 user 정보를 저장함
        // getPrincipal : user id 값이 저장되어 있음
/*        User user_info = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String user_id = user_info.getUsername();*/

        String user_id = "iieunji023@gmail.com";

        // Room 정보 가져오기
        AdminReservationDto adminReservation = iUserReservationDaoMapper.selectRoomInfo(adminReservationDto);
        log.info("adminReservation" + adminReservation);

        adminReservationDto.setU_m_email(user_id);

        // Room 예약 정보 db에 저장
//        int result = iUserReservationDaoMapper.insertRsvInfo(adminReservationDto);
        // 모달창에서 입력받은 예약 날짜, 가격, state
        int result = iUserReservationDaoMapper.selectRoomModal(adminReservation);

        if(result > 0) {
            log.info("예약 완료");

            return "success";
            
        } else {
            log.info("예약 실패 ㅠ_ㅠ");

            return "fail";

        }

    }

}
