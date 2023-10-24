package com.btc.swimpyo.backend.config;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private String uploadDir = "C:\\localImage\\";

    public String imageUpload(MultipartFile uploadFile) throws IOException {


        // 뽑아낸 이미지 파일에서 이름 및 확장자 추출
        String fileOriName = uploadFile.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());

        // 이미지 파일 이름 유일성을 위해 uuid 생성
        UUID uuid = UUID.randomUUID();
        String uniqueName = uuid.toString().replace("-", "");

        File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
        uploadFile.transferTo(saveFile);

        return uniqueName + fileExtension;


    }

}
