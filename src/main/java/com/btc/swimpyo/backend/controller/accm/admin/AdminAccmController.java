package com.btc.swimpyo.backend.controller.accm.admin;

//import com.btc.swimpyo.backend.config.UploadFileService;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.service.accm.admin.AdminAccmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api/admin/accm")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminAccmController {

    private final AdminAccmService adminAccmService;

//    private final UploadFileService uploadFileService;

    // 등록
    @GetMapping("/regist_form")
    public void registFrom() {
        log.info("[AdminAccmController] registFrom()");

    }

    /*@PostMapping(value="/regist_confirm", consumes="multipart/form-data")
    public void registConfirm(AdminAccmDto adminAccmDto, @RequestPart(value = "a_acc_image", required = false) MultipartFile[] files) {
        log.info("[AdminAccmController] registConfirm()");

        log.info("[AdminAccmController] dto : " + adminAccmDto);

        String saveFileName = "noImage";

        // SAVE FILE
        for (MultipartFile file1 : files) {
            if(file1 != null && !file1.isEmpty()) {
                saveFileName = uploadFileService.upload(file1);
            }
        }

        adminAccmService.registConfirm(saveFileName, adminAccmDto);

    }*/

    /*@PostMapping(value="/regist_confirm", consumes="multipart/form-data")
    public String registConfirm(@RequestPart(value="adminAccmDto", required = false)AdminAccmDto adminAccmDto, @RequestPart(value="a_acc_image", required = false) MultipartFile a_acc_image, Model model) {
        log.info("[AdminAccmController] registConfirm()");

        log.info("[AdminAccmController] dto : " + adminAccmDto);

        try {
            InputStream inputStream = a_acc_image.getInputStream();
            // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
        } catch (IOException e) {
            log.error("Error getting InputStream from MultipartFile", e);
            // 에러 처리 로직 추가
        }

        // S3에 이미지 업로드하고 URL을 얻어옴
        String imageUrl = adminAccmService.registConfirm(adminAccmDto, a_acc_image);
        log.info("[imageUrl] : " + imageUrl);

        // View로 전달할 데이터를 Model에 추가0
        model.addAttribute("imageUrl", imageUrl);

        // 적절한 View 이름으로 변경
        return imageUrl;
    }

    // S3
    @PostMapping(path = "/S3", consumes="multipart/form-data")
    public ResponseEntity createS3(String a_acc_image,
                                   @RequestPart(value = "file", required = false) MultipartFile file){
        adminAccmService.createS3(a_acc_image, file);
        return new ResponseEntity(null, HttpStatus.OK);
    }*/

    @PostMapping(value="/regist_confirm", consumes="multipart/form-data")
    public String registConfirm(@RequestPart(value="adminAccmDto", required = false)AdminAccmDto adminAccmDto, @RequestPart(value="a_acc_image", required = false) MultipartFile[] a_acc_images, Model model) {
        log.info("[AdminAccmController] registConfirm()");

        log.info("[AdminAccmController] dto : " + adminAccmDto);


        try {
            for (MultipartFile a_acc_image : a_acc_images) {
                InputStream inputStream = a_acc_image.getInputStream();
                // 이제 inputStream을 사용하여 파일을 처리할 수 있습니다.
            }
        } catch (IOException e){
            log.error("MultipartFile에서 InputStream을 가져오는 중 에러 발생", e);
            //
        }

        // S3에 이미지 업로드하고 URL을 얻어옴
        String imageUrl = adminAccmService.registConfirm(adminAccmDto, a_acc_images);
        log.info("[imageUrl] : " + imageUrl);

        // View로 전달할 데이터를 Model에 추가0
        model.addAttribute("imageUrl", imageUrl);

        // 적절한 View 이름으로 변경
        return imageUrl;
    }

    // S3
    @PostMapping(path = "/S3", consumes="multipart/form-data")
    public ResponseEntity createS3(@RequestPart(value = "file", required = false) MultipartFile[] files){
        adminAccmService.createS3(files);
        return new ResponseEntity(null, HttpStatus.OK);
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
    @PostMapping("/delete_accm")
    public int deleteAccm(@RequestBody AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmController] deleteAccm()");

        int a_m_no = adminAccmDto.getA_m_no();

        return adminAccmService.deleteAccm(a_m_no);

    }

}
