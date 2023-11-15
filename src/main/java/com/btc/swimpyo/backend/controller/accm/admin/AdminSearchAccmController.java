package com.btc.swimpyo.backend.controller.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.admin.IAdminSearchAccmService;
import com.btc.swimpyo.backend.service.accm.user.ISearchAccmService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/admin/accm")
@RequiredArgsConstructor
public class AdminSearchAccmController {

    private final IAdminSearchAccmService iAdminSearchAccmService;

    // 관리자 예약 리스트
    @GetMapping("/rezList")
    public Object rezList(@RequestParam ("a_m_no") int a_m_no) {
        log.info("rezList");

        List<Map<String, Object>> map = iAdminSearchAccmService.rezList(a_m_no);

        return map;
    }

    // 관리자 숙소 등록 확인
    @GetMapping("/checkAccm")
    public Object checkAccm(@RequestParam ("a_m_no") int a_m_no) {
        log.info("checkAccm");

        int result = iAdminSearchAccmService.checkAccm(a_m_no);
        log.info("result = {}", result);

        return result;
    }

}
