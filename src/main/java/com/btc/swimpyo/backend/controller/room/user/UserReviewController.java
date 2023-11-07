package com.btc.swimpyo.backend.controller.room.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/user/room")
@RequiredArgsConstructor
public class UserReviewController {

    @PostMapping("/registReview")
    public Object registReview(){
        log.info("registReview");

        return null;
    }

}
