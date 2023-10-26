//package com.btc.swimpyo.backend.controller;
//
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.util.IOUtils;
//import com.btc.swimpyo.backend.service.S3Service;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@Log4j2
//@RestController
//@RequestMapping("/api/images")
//public class ImageController {
//
//    private final S3Service s3Service;
//
//    public ImageController(S3Service s3Service) {
//        this.s3Service = s3Service;
//    }
//
//    @GetMapping("/showImage/{imageName}")
//    public String showImage(@PathVariable String imageName, Model model) {
//        String imageUrl = s3Service.getObjectUrl(imageName).toString();
//        model.addAttribute("imageUrl", imageUrl);
//
//        log.info("[showImage] : " + imageUrl);
//
//        return imageUrl; // 적절한 View 이름으로 변경
//
//    }
//
//}
