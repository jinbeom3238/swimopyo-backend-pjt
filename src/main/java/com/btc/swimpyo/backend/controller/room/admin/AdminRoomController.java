package com.btc.swimpyo.backend.controller.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import com.btc.swimpyo.backend.service.room.admin.AdminRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/admin/room")
@RequiredArgsConstructor
public class AdminRoomController {

    private final AdminRoomService adminRoomService;

    // 등록
    @PostMapping(value="/registConfirm", consumes="multipart/form-data")
    public String registConfirm(@RequestPart(value="adminRoomDto", required = false)AdminRoomDto adminRoomDto, @RequestPart(value="r_i_image", required = false) MultipartFile[] r_i_images) {
        log.info("[AdminRoomController] registConfirm()");

        try {
            // a_i_images의 값을 토대로, 차례대로 data를 뽑아낼 수 있다.
            for (MultipartFile file : r_i_images) {
                // InputStream은 데이터를 byte 단위로 읽어들이는 통로 (읽어들인 데이터를 byte로 돌려줌)
                InputStream inputStream = file.getInputStream();
                // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
                log.info("[AdminAccmController] inputStream: " + inputStream);

            }
        } catch (IOException e){
            log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);

        }

        // S3에 이미지 업로드하고 URL을 얻어옴
        String imageUrl = adminRoomService.registConfirm(adminRoomDto, r_i_images);

        log.info("[imageUrl] : " + imageUrl);
        log.info("[AdminAccmController] dto : " + adminRoomDto);

        return imageUrl;

    }

    // 상세 페이지 조회
    @PostMapping("/showRoomDetail")
    public Map<String, Object> showRoomDetail(@RequestParam("a_m_no") int a_m_no) {
        log.info("[AdminAccmController] showRoomDetail()");

        AdminRoomDto adminRoomDto = new AdminRoomDto();
        AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
        adminRoomDto.setR_i_image(adminRoomImageDto.getR_i_image());

        log.info("[AdminAccmController] a_m_no : " + a_m_no);
        Map<String, Object> msgData = adminRoomService.showRoomDetail(a_m_no);

        return msgData;

    }




    // 수정
    
    // 삭제
    
}
