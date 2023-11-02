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
import java.util.HashMap;
import java.util.List;
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
    public Map<String, Object> showRoomDetail(@RequestParam("a_r_no") int a_r_no, AdminRoomDto adminRoomDto) {
        log.info("[AdminAccmController] showRoomDetail()");

//        AdminRoomDto adminRoomDto = new AdminRoomDto();
        AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
        adminRoomDto.setR_i_image(adminRoomImageDto.getR_i_image());

        log.info("[AdminAccmController] a_m_no : " + a_r_no);
        Map<String, Object> msgData = adminRoomService.showRoomDetail(a_r_no);

        return msgData;

    }

    // 수정
    @PostMapping(value = "modifyConfirm", consumes="multipart/form-data")
    public void modifyConfirm(@RequestPart(value = "adminRoomDto", required = false) AdminRoomDto adminRoomDto,
                              @RequestPart(value = "r_i_image", required = false) MultipartFile[] r_i_images,
                              @RequestParam(value = "deleteNo", required = false) List<Integer> deleteNos) {
        log.info("[AdminRoomController] modifyConfirm()");

        log.info("[AdminRoomController] deleteNos : {}", deleteNos);

        // 추가된 이미지가 있다면
        if(r_i_images != null) {
            try{
                log.info("추가할 이미지가 존재합니다.");
                for (MultipartFile r_i_image : r_i_images) {
                    InputStream inputStream = r_i_image.getInputStream();
                    // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
                    log.info("[AdminRoomController] inputStream: " + inputStream);
                }

            }
            catch (IOException e) {
                log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);

            }

        }
        // S3에 이미지를 업로드하고 url을 얻어옴
        adminRoomService.modifyConfirm(adminRoomDto, r_i_images, deleteNos);

        log.info("[AdminRoomController] adminRoomDto: {}", adminRoomDto);

    }

    // 삭제
    @PostMapping("/deleteConfirm")
    public int deleteConfirm(@RequestBody AdminRoomDto adminRoomDto) {
        log.info("[AdminRoomController] deleteConfirm()");

        int a_m_no = adminRoomDto.getA_m_no();
        log.info("a_m_no: {}", a_m_no );



        return adminRoomService.deleteConfirm(a_m_no);

    }

    // Room 리스트 조회 - 숙박시설 상세 페이지에서 보여지는 부분
    @PostMapping("/showRoomList")
    public Map<String, Object> showRoomList(@RequestParam("a_acc_no") int a_acc_no) {
        log.info("[AdminAccmController] showRoomList()");

        Map<String, Object> msgData = new HashMap<>();

        msgData = adminRoomService.showRoomList(a_acc_no);

        return msgData;

    }

}
