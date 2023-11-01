package com.btc.swimpyo.backend.utils;

import com.amazonaws.SdkClientException;
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

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*@Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;*/

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
            // ACL 설정 -  S3 객체를 공개 읽기 권한으로 설정(모든 사용자가 읽기 액세스를 할 수 있음)
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
        log.info("fileName --> {} ", fileName);

        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, "static/test/" + fileName)); // "static/test/"를 추가하여 경로를 지정
            log.info("[S3Uploader] File deleted from S3: " + fileName);

        } catch (AmazonServiceException e) {
            log.error("[S3Uploader] Error deleting file from S3: " + fileName, e);
        }
    }

    /*public void deleteFileFromS3(String key) {
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, key);
            //Delete
            log.info("deleteObject 호출 전: bucket={}, key={}", this.bucket, key);
            this.amazonS3Client.deleteObject(deleteObjectRequest);
            log.info("deleteObject 호출 후: bucket={}, key={}", this.bucket, key);

            log.info(String.format("[%s] deletion complete", key));

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }*/

    // MultipartFile을 받아서 file 객체로 변환하는 과정
    public Optional<File> convert(MultipartFile file) throws IOException {
        // 임시 디렉토리에 파일을 생성
        String dirPath = System.getProperty("java.io.tmpdir");
        // getOriginalFilename() -> 업로드되는 파일에서 확장자를 포함한 파일의 이름을 반환
        String fileName = file.getOriginalFilename() + UUID.randomUUID();
        File convertFile = new File(dirPath + File.separator + fileName);

        // FileOutputStream을 사용하여 데이터를 파일에 바이트 스트림으로 저장한다.
        // createNewFile() => 새로운 파일을 생성 메서드, 이미 존재하는 경우 false를 반환
        if (convertFile.createNewFile()) {
            // FileOutputStream은 파일을 작성하기 위해서 사용한다.
            // 주어진 File 객체가 가리키는 파일을 쓰기 위한 객체를 생성
            //기존의 파일이 존재할 때는 그 내용을 지우고 새로운 파일을 생성.
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                // 문자열을 바이트배열로 변환해서 파일에 저장한다.
                // write()은 내용을 추가해 주는 역할을 한다.
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

}