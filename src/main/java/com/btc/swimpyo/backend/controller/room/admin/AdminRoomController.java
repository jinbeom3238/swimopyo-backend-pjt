package com.btc.swimpyo.backend.controller.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.service.room.admin.AdminRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/admin/room")
@RequiredArgsConstructor
public class AdminRoomController {

    private final AdminRoomService adminRoomService;

    // 등록
    @GetMapping("/regist_form")
    public void registForm() {
        log.info("[AdminRoomController] registForm()");

    }

    @PostMapping("/regist_confirm")
    public int registConfirm(@RequestParam("a_acc_no") int a_acc_no, @RequestBody AdminRoomDto adminRoomDto) {
        log.info("[AdminRoomController] registConfirm()");

        return adminRoomService.registConfirm(a_acc_no, adminRoomDto);

    }

    // 수정
    
    // 삭제
    
}
