//package com.btc.swimpyo.backend.service;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.AmazonS3Exception;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.S3Object;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;
//import java.net.URL;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    /*public S3Service(AmazonS3Client amazonS3Client) {
//        this.amazonS3Client = amazonS3Client;
//    }*/
//
//    public URL getObjectUrl(String key) {
//        try {
//
//            log.info("[S3Service] getObjectUrl()");
//
//            S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, key));
//
//            URL url = s3Object.getObjectContent().getHttpRequest().getURI().toURL();
//
//            log.info("Got URL: " + url); // 추가된 로깅 코드
//
//            return url;
//
//        } catch (Exception e) {
//            log.error("Error getting S3 object URL", e);
//            return null;
//        }
//    }
//}
