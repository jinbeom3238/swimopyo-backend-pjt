package com.btc.swimpyo.backend.controller.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.user.ISearchAccmService;
import com.btc.swimpyo.backend.service.accm.user.SearchAccmService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/accm")
@RequiredArgsConstructor
public class SearchAccmController {

    private final ISearchAccmService searchAccmService;

    @GetMapping("/search")
    public Object searchAccm(@RequestParam Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
        log.info("searchAccm");
        List<AdminAccmDto> searchAccms = searchAccmService.searchAccm(msgMap, adminAccmDto);

        log.info("msgMap ==> {}", msgMap);

        return searchAccms;
//        return null;
    }



}
