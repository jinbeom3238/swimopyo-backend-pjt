package com.btc.swimpyo.backend.controller.room.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.service.room.user.UserReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user/review")
@RequiredArgsConstructor
public class UserReviewController {

    private final UserReviewService userReviewService;

    // 등록
    @PostMapping(value = "/registConfirm", consumes = "multipart/form-data")
    public String registConfirm(@RequestPart(value = "userReviewDto", required = false) UserReviewDto userReviewDto,
                                             @RequestPart(value = "userReservationDto", required = false) ReservationDto reservationDto,
                                             @RequestPart (value = "address", required = false) List<UserReviewDto> address,
                                             @RequestPart(value = "reviewImages", required = false) MultipartFile[] reviewImages) {
        log.info("[UserReviewController] registReview()");

        log.info("reservationDto: " + reservationDto);
        userReviewDto.setU_r_no(reservationDto.getU_r_no());
        log.info("reviewImages" + reviewImages);
        log.info("r_xy_address" + address);

        if(reviewImages != null) {
            try {
                // reviewImages 차례대로 data를 뽑음
                for (MultipartFile file : reviewImages) {
                    InputStream inputStream = file.getInputStream();

                    log.info("inputstream:" + inputStream);

                }
            } catch (IOException e) {
                log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);

            }
        }
        String imageUrl = userReviewService.registConfirm(userReviewDto, address, reviewImages);

        return imageUrl;

    }

    // [숙박시설 상세페이지] 리스트 조회
    @GetMapping("showReviewList")
    public List<Map<String, Object>> showReviewList(@RequestParam("a_acc_no") int a_acc_no) {
        log.info("[UserReviewController] showReviewList()");

        return userReviewService.showReviewList(a_acc_no);

    }

    // [룸 상세페이지] 리스트 조회
    @GetMapping("showReviewListRoom")
    public Map<String, Object> showReviewListRoom(@RequestParam("a_r_no") int a_r_no,
                                                  @RequestParam("a_acc_no") int a_acc_no) {
        log.info("[UserReviewController] showReviewListRoom()");

        return userReviewService.showReviewListRoom(a_r_no, a_acc_no);

    }

    // [숙박업소] 상세페이지 조회
    @GetMapping("/showDetail")
    public Map<String, Object> showDetail(@RequestParam("r_no") int r_no) {
        log.info("[UserReviewController] showDetail()");

        return userReviewService.showDetail(r_no);

    }

    // 삭제
    @PostMapping("/deleteConfirm")
    public int deleteConfirm(@RequestPart UserReviewDto userReviewDto, @RequestParam("r_no") int r_no, @RequestParam("u_m_email") String u_m_email) {
        log.info("[UserReviewController] deleteConfirm()");

        log.info("userReviewDto: " + userReviewDto);
        log.info("userReviewDto: " + userReviewDto.getR_no());
        log.info("userReviewDto: " + userReviewDto.getU_m_email());

        return userReviewService.deleteConfirm(userReviewDto);

    }

}
