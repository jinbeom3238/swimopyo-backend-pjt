package com.btc.swimpyo.backend.controller.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.user.UserAccmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/user/accm")
@RequiredArgsConstructor
public class UserAccmController {

    private final UserAccmService userAccmService;

    // 리스트 조회
    @PostMapping("/showAccmList")
    public Map<String, Object> showAccmList(AdminAccmDto adminAccmDto) {
        log.info("[UserAccmController] showAccmList()");

        return userAccmService.showAccmList(adminAccmDto);

    }

    // 숙박시설 상세페이지 조회
    @PostMapping("/showAccmDetail")
    public Map<String, Object> showAccmDetail(@RequestParam("a_acc_no") int a_acc_no) throws JsonProcessingException {
        log.info("[UserAccmController] showAccmDetail()");
        // 경위도 정보를 가져오는 메소드 호출
//        longitude = "126.9784";  // 경도 값 설정
//        latitude = "37.5665";   // 위도 값 설정
//        ResponseEntity<String> response = kakaoMapApiController.getAddressFromCoords(longitude, latitude);
//        String address = response.getBody();

//        log.info("[AdminAccmController] Address: " + address);

        return userAccmService.showAccmDetail(a_acc_no);

    }

}
