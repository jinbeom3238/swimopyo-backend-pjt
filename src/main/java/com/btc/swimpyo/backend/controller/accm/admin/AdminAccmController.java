package com.btc.swimpyo.backend.controller.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.admin.AdminAccmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/admin/accm")
public class AdminAccmController {

    private AdminAccmService adminAccmService;

    public AdminAccmController(AdminAccmService adminAccmService) {

        this.adminAccmService = adminAccmService;

    }

    // 등록
    @GetMapping("/regist_form")
    public void registFrom() {
        log.info("[AdminAccmController] registFrom()");

    }

    @PostMapping("/regist_confirm")
    public void registConfirm(@RequestBody AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmController] registConfirm()");

        log.info("[AdminAccmController] dto : " + adminAccmDto);

        adminAccmService.registConfirm(adminAccmDto);

    }

    // 상세페이지 조회
    @PostMapping("/show_accm_detail")
    public AdminAccmDto showAccmDetail(@RequestParam("a_m_no") int a_m_no) {
        log.info("[AdminAccmController] showAccmDetail()");

        AdminAccmDto adminAccmDtos = adminAccmService.showAccmDetail(a_m_no);

        log.info("adminAccmDtos: " + adminAccmDtos);

        return adminAccmDtos;

    }

    // 수정
    @PostMapping("modify_confirm")
    public int modifyConfirm(@RequestBody AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmController] modifyConfirm()");

        return adminAccmService.modifyConfirm(adminAccmDto);

    }

    // 삭제
    @PostMapping("delete_accm")
    public int deleteAccm(@RequestBody AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmController] deleteAccm()");

        int a_m_no = adminAccmDto.getA_m_no();

        return adminAccmService.deleteAccm(a_m_no);

    }

}
