package com.btc.swimpyo.backend.controller.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.user.UserAccmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("user_accm")
public class UserAccmController {

    private UserAccmService userAccmService;

    public UserAccmController(UserAccmService userAccmService) {
        this.userAccmService = userAccmService;
    }

    // 리스트 조회
    @PostMapping("/show_accm_list")
    public List<AdminAccmDto> showAccmList(AdminAccmDto adminAccmDto) {
        log.info("[UserAccmController] showAccmList()");

        return userAccmService.showAccmList(adminAccmDto);

    }

    // 숙박시설 상세페이지 조회
    @PostMapping("/show_accm_detail")
    public AdminAccmDto showAccmDetail(@RequestParam("a_acc_no") int a_acc_no){
        log.info("[UserAccmController] showAccmDetail()");

        return userAccmService.showAccmDetail(a_acc_no);

    }



}
