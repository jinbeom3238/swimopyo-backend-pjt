package com.btc.swimpyo.backend.controller.reservation.user;

import com.btc.swimpyo.backend.service.Reservation.user.UserReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/user/reservation")
@RequiredArgsConstructor
public class UserReservationController {

    private final UserReservationService userReservationService;

}
