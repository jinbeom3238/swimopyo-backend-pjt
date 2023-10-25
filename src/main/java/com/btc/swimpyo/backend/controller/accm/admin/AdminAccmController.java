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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api/admin/accm")
@RequiredArgsConstructor
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

    @PostMapping(value="/regist_confirm", consumes="multipart/form-data")
    public void registConfirm(AdminAccmDto adminAccmDto, @RequestPart(value = "a_acc_image") MultipartFile a_acc_image) {
        log.info("[AdminAccmController] registConfirm()");

        log.info("[AdminAccmController] dto : " + adminAccmDto);

        adminAccmService.registConfirm(adminAccmDto, a_acc_image);

    }

    // S3
    @PostMapping(path = "/S3", consumes="multipart/form-data")
    public ResponseEntity createS3(String a_acc_image,
                                   @RequestPart(value = "file", required = false) MultipartFile file){
        adminAccmService.createS3(a_acc_image, file);
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
