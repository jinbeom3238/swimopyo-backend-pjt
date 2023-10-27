package com.btc.swimpyo.backend.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFileToS3(MultipartFile multipartFile, String filePath) {
        // MultipartFile -> File 로 변환
        File uploadFile = null;
        log.info("[uploadFileToS3] MultipartFile-------> {}",  multipartFile);

        try {
            uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("[error]: MultipartFile -> 파일 변환 실패"));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        // S3에 저장될 파일 이름을 UUID로 생성
        String fileName = filePath + "/" + UUID.randomUUID();

        // s3로 업로드 후 로컬 파일 삭제
        String uploadImageUrl = putS3(multipartFile, fileName);
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    public String putS3(MultipartFile multipartFile, String fileName) {

        // 새로운 객체 메타데이터 생성(S3에 업로드할 파일의 속성을 나타냄)
        ObjectMetadata metadata = new ObjectMetadata();
        String contentType = multipartFile.getContentType();
        metadata.setContentType("image/jpeg");
//        metadata.setContentType(contentType);
        metadata.setContentLength(multipartFile.getSize());

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            e.printStackTrace();

            return null;

        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("[파일 업로드] : 파일 삭제 성공");
            return;
        }
        log.info("[파일 업로드] : 파일 삭제 실패");
    }


    public void deleteFileFromS3(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
            log.info("[S3Uploader] File deleted from S3: " + fileName);
        } catch (AmazonServiceException e) {
            log.error("[S3Uploader] Error deleting file from S3: " + fileName, e);
        }
    }


    // MultipartFile을 받아서 로컬 파일로 변환하는 과정
    public Optional<File> convert(MultipartFile file) throws IOException {
        // 임시 디렉토리에 파일을 생성합니다.
        String dirPath = System.getProperty("java.io.tmpdir");
        String fileName = file.getOriginalFilename();
        File convertFile = new File(dirPath + File.separator + fileName);

        if (convertFile.createNewFile()) {
            // FileOutputStream을 사용하여 데이터를 파일에 바이트 스트림으로 저장합니다.
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }


}