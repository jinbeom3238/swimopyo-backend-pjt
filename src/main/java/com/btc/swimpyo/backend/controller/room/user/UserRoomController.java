package com.btc.swimpyo.backend.controller.room.user;

import com.btc.swimpyo.backend.service.room.user.UserRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/user/room")
@RequiredArgsConstructor
public class UserRoomController {

    private final UserRoomService userRoomService;

    // 룸 리스트 조회
    @PostMapping("/showRoomList")
    public Map<String, Object> showRoomList(@RequestParam("a_acc_no") int a_acc_no) {
        log.info("[UserRoomController] showRoomList()");

        return userRoomService.showRoomList(a_acc_no);

    }

    // 룸 상세페이지 조회
    @PostMapping("/showRoomDetail")
    public Map<String, Object> showRoomDetail(@RequestParam("a_r_no") int a_r_no) {
        log.info("[UserRoomController] showRoomDetail()");

        return userRoomService.showRoomDetail(a_r_no);

    }
    
}
