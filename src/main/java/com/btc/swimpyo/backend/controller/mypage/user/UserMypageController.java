package com.btc.swimpyo.backend.controller.mypage.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.service.mypage.user.UserMypageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/mypage")
@RequiredArgsConstructor
public class UserMypageController {

    private final UserMypageService userMypageService;

    @GetMapping("/GetRezList")
    public Object GetRezList(@RequestParam(value = "u_r_no", defaultValue="0") int u_r_no,
                             @RequestParam(value = "period", defaultValue = "0") int period,
                             HttpServletRequest request){
        log.info("GetRezList");

        List<Map<String,Object>> GetRezList = userMypageService.GetRezList(request, u_r_no, period);
        if(GetRezList == null){
            return "GetRezListFail";
        }
        return GetRezList;
    }

    @GetMapping("/GetRezDetail")
    public Object GetRezDetail(@RequestParam(value = "u_r_no", defaultValue="0") int u_r_no,
                               HttpServletRequest request){
        log.info("GetRezDetail");

        Map<String,Object> GetRezDetail = userMypageService.GetRezDetail(request, u_r_no);
        if(GetRezDetail == null){
            return "GetRezDetailFail";
        }
        return GetRezDetail;
    }

//     [마이페이지] 리스트 조회
    @PostMapping("/getReviewList")
    public Object showReviewList(HttpServletRequest request) {
        log.info("showReviewList");

//        Map<String, Object> map = userMypageService.showReviewList(request);
//        if(map == null){
//            return "getReviewListFail";
//        }
//        return map;
        return null;

    }

}
