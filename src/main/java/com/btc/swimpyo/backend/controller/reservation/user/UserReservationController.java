package com.btc.swimpyo.backend.controller.reservation.user;

import com.btc.swimpyo.backend.dto.reservation.admin.AdminReservationDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.service.Reservation.user.UserReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/user/reservation")
@RequiredArgsConstructor
public class UserReservationController {

    private final UserReservationService userReservationService;

    // 기간(날짜), 방 체크(예약이 되어 있는지)
    // 1. 예약은 룸 상세 페이지에서 가능하다.
    // 2. 예약 가능한 방만 예약할 수 있다.
    // 3. 예약이 가능하다면,날짜, 숙박/대실, 가격을 선택한다.
    // 4. 선택 후 결제하기 버튼을 클릭하면 결제 페이지로 이동한다.
    // 5. 결제 페이지에는 룸 정보, 예약 날짜, 가격이 나오고, 실사용자의 이름과 연락처를 입력한다.
    // 6. 모두 입력하면 결제하기 버튼을 통해 카카오페이로 결제할 수 있다.

    // 예약하기 버튼 클릭 시 예약 페이지(모달창)
    @PostMapping("")
    public String createReservation(@RequestPart AdminReservationDto adminReservationDto){
        log.info("[UserReservationController] createReservation()");
        log.info("[UserReservationController] adminReservaitionDto(): " + adminReservationDto);

        return userReservationService.createReservation(adminReservationDto);

    }

    // 결제 페이지
//    @GetMapping("")
    


}