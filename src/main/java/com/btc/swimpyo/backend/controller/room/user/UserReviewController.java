package com.btc.swimpyo.backend.controller.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.service.room.user.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/room")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    @PostMapping("/registReview")
    public Object registReview(@RequestBody Map<String, Object> msgMap, UserReviewDto userReviewDto){
        log.info("registReview");

        int result = -1;
        result  = userReviewService.registReview(msgMap, userReviewDto);
        if(result > 0){
            return "userRegReviewSuccess";
        } else {
            return "userRegReviewFail";
        }
    }

    @PostMapping("/getReview")
    public Object getReview(@RequestBody Map<String, Object>  msgMap, UserReviewDto userReviewDto){
        log.info("getReview");


        return null;
    }

}
