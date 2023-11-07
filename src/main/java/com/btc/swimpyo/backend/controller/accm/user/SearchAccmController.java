package com.btc.swimpyo.backend.controller.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.user.ISearchAccmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/accm")
@RequiredArgsConstructor
public class SearchAccmController {

    private final ISearchAccmService searchAccmService;

    @PostMapping("/search")
    public Object searchAccm(@RequestBody Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
        log.info("searchAccm");
        List<AdminAccmDto> searchAccms = searchAccmService.searchAccm(msgMap, adminAccmDto);

        log.info("searchAccms ==> {}", searchAccms);

        return searchAccms;
    }



}
