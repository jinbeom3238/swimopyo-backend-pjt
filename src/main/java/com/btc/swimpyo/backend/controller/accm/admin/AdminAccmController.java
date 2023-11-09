package com.btc.swimpyo.backend.controller.accm.admin;

import com.btc.swimpyo.backend.controller.api.KakaoMapApiController;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.service.accm.admin.AdminAccmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/admin/accm")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminAccmController {

    private final AdminAccmService adminAccmService;
//    private final KakaoMapApiController kakaoMapApiController;

    // 등록
    @PostMapping(value="/regist_confirm", consumes="multipart/form-data")
    public String registConfirm(@RequestPart(value="adminAccmDto", required = false)AdminAccmDto adminAccmDto, @RequestPart(value="a_i_image", required = false) MultipartFile[] a_i_images) {
        log.info("[AdminAccmController] registConfirm()");

        try {
            // a_i_images의 값을 토대로, 차례대로 data를 뽑아낼 수 있다.
            for (MultipartFile a_i_image : a_i_images) {
                // InputStream은 데이터를 byte 단위로 읽어들이는 통로 (읽어들인 데이터를 byte로 돌려줌)
                InputStream inputStream = a_i_image.getInputStream();
                // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
                log.info("[AdminAccmController] inputStream: " + inputStream);

            }
        } catch (IOException e){
            log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);

        }

        // S3에 이미지 업로드하고 URL을 얻어옴
        String imageUrl = adminAccmService.registConfirm(adminAccmDto, a_i_images);

        log.info("[imageUrl] : " + imageUrl);
        log.info("[AdminAccmController] dto : " + adminAccmDto);

        return imageUrl;
    }

    @PostMapping("/show_accm_detail")
    public Map<String, Object> showAccmDetail(@RequestParam(value = "a_m_no", required = false) int a_m_no) {
        log.info("[AdminAccmController] showAccmDetail()");

        AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();
        AdminAccmDto adminAccmDto = new AdminAccmDto();
        adminAccmDto.setA_i_image(adminAccmImageDto.getA_i_image());

        log.info("[AdminAccmController] a_m_no : " + a_m_no);
        // DTO, IMAGE를 같이 보여주기 위해 Map에 담음, image가 배열이기 때문에 map으로 반환
        Map<String, Object> msgData = adminAccmService.showAccmDetail(a_m_no);
        log.info("[AdminAccmController] msgData : " + msgData.get("adminAccmDto"));
        log.info("[AdminAccmController] msgData : " + msgData.get("a_i_images"));



        return msgData;
    }

    // 수정
    // consumes는 들어오는 데이터 타입을 정의할때 이용
    @PostMapping(value = "modify_confirm", consumes="multipart/form-data")
    public void modifyConfirm(@RequestPart(value="adminAccmDto", required = false) AdminAccmDto adminAccmDto, @RequestPart(value="a_i_image", required = false) MultipartFile[] a_i_images, @RequestParam(value = "deleteImg", required = false) List<Integer> deleteImgs) {
        log.info("[AdminAccmController] modifyConfirm()");

        log.info("deleteImgs: {}", deleteImgs);

        // 추가된 이미지가 있다면
        if(a_i_images != null) {
            try {
                for (MultipartFile a_i_image : a_i_images) {
                    InputStream inputStream = a_i_image.getInputStream();
                    // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
                    log.info("[AdminAccmController] inputStream: " + inputStream);

                }
            } catch (IOException e) {
                log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);

            }
        }
        // S3에 이미지 업로드하고 URL을 얻어옴
        adminAccmService.modifyConfirm(adminAccmDto, a_i_images, deleteImgs);

//        log.info("[imageUrl] : " + imageUrl);
        log.info("[AdminAccmController] dto : " + adminAccmDto);

    }

    // 삭제
    @PostMapping("/delete_confirm")
    public int deleteAccm(@RequestBody AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmController] deleteAccm()");

        int a_m_no = adminAccmDto.getA_m_no();

        return adminAccmService.deleteAccm(a_m_no);

    }

}