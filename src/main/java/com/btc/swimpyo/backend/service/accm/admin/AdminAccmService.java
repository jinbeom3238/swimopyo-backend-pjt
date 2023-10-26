package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.mappers.accm.admin.IAdminAccmDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@Transactional
public class AdminAccmService implements IAdminAccmService {


    private IAdminAccmDaoMapper iAdminAccmDaoMapper;
    private final S3Uploader s3Uploader;

    @Autowired
    public AdminAccmService(IAdminAccmDaoMapper iAdminAccmDaoMapper, S3Uploader s3Uploader) {
        this.iAdminAccmDaoMapper = iAdminAccmDaoMapper;
        this.s3Uploader = s3Uploader;
    }


    /*public AdminAccmService(IAdminAccmDaoMapper iAdminAccmDaoMapper, S3Uploader s3Uploader){
        this.iAdminAccmDaoMapper = iAdminAccmDaoMapper;
        this.s3Uploader = s3Uploader;
    }*/

    // 등록
    /*@Override
    public void registConfirm(String a_acc_image, AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmService] registConfirm()");

        *//*adminAccmDto.setA_m_name((String) data.get("a_m_name"));
        adminAccmDto.setA_m_email((String) data.get("a_m_email"));*//*

        adminAccmDto.setA_acc_image(a_acc_image);

        Map<String, Object> msgData = new HashMap<>();
        msgData.put("registInfo", adminAccmDto);
        log.info("msgData : " + msgData);

        // 예외처리 - a_m_no 당 1개의 숙박시설만 등록할 수 있으므로 unique. 그러나 중복된 값을 넣는 경우 오류가 발생하므로 예외처리해주었음
        *//*try {

            iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);
            log.info("a_m_no INSERT SUCCESS!!");
            
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.info("a_m_no ALREADY EXISTS!!");
                
            } else {
                e.printStackTrace();
                log.info("a_m_no INSERT FAIL!!");
                
            }
        }*//*


    }*/
/*
    @Override
    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile a_acc_image) {
        log.info("[AdminAccmService] registConfirm()");

        // S3에 이미지 업로드하고 URL을 얻어옴
        String imageUrl = s3Uploader.uploadFileToS3(a_acc_image, "static/test");

        // a_acc_image 필드에 URL 설정
        adminAccmDto.setA_acc_image(imageUrl);

        Map<String, Object> msgData = new HashMap<>();
        msgData.put("registInfo", adminAccmDto);
        log.info("msgData : " + msgData);

        iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);

        return imageUrl; // 업로드된 이미지의 URL 반환
    }

    @Override
    @Transactional
    public void createS3(String a_acc_image, MultipartFile file) {
        String url = "";
        if(file != null)  url = s3Uploader.uploadFileToS3(file, "static/test");

    }*/





    /*@Override
    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_images) {
        log.info("[AdminAccmService] registConfirm()");

        List<String> imageUrls = new ArrayList<>(); // 여러 이미지의 URL을 저장할 리스트

        // 여러 이미지를 업로드하고 각각의 URL을 얻어옴
        for (MultipartFile a_i_image : a_i_images) {
            String imageUrl = s3Uploader.uploadFileToS3(a_i_image, "static/test");
            imageUrls.add(imageUrl); // 리스트에 추가
        }

        // 첫 번째 이미지 URL을 a_acc_image 필드에 설정 (예시로 첫 번째 이미지를 설정하도록 했습니다)
        if (!imageUrls.isEmpty()) {
            adminAccmDto.setA_i_image(imageUrls.get(0));
        }

        Map<String, Object> msgData = new HashMap<>();
        msgData.put("registInfo", adminAccmDto);
        log.info("msgData : " + msgData);

        iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);

        return !imageUrls.isEmpty() ? imageUrls.get(0) : null; // 첫 번째 이미지의 URL 반환
    }*/

    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_images) {
        log.info("[AdminAccmService] registConfirm()");
        log.info("[AdminAccmService] dto : " + adminAccmDto);
        log.info("[AdminAccmService] a_i_image : " + a_i_images);

        try {
            // 1. tbl_admin_accommodation 테이블에 데이터 등록
            iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);

            // 2. 등록된 숙박시설의 번호 가져오기
            int a_acc_no = iAdminAccmDaoMapper.selectAccmForAmNo(adminAccmDto.getA_m_no());
            log.info("[AdminAccmService] a_acc_no: " + a_acc_no);

            // AdminAccmImageDto 객체 생성
            AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();
            adminAccmImageDto.setA_acc_no(a_acc_no);

            // 3. tbl_accommodation_image 테이블에 이미지 정보 등록
            for (MultipartFile a_i_image : a_i_images) {

                log.info("[AdminAccmService] a_i_image: -----> {}", a_i_image);

                String imageUrl = s3Uploader.uploadFileToS3(a_i_image, "static/test");

                adminAccmImageDto.setA_i_image(imageUrl);

                log.info("[AdminAccmService] imageUrl: " + imageUrl);

//                int result = iAdminAccmDaoMapper.insertAccmImage(a_acc_no, imageUrl);
                int result = iAdminAccmDaoMapper.insertAccmImage(adminAccmImageDto);

//                Map<String, Object> msgData = new HashMap<>();
//                msgData.put("registInfo", adminAccmDto);
//                log.info("msgData : " + msgData);

                log.info("result: " + result);

            }
            // 4. 이미지 업로드가 완료되면 이미지 URL을 반환
            return "이미지 업로드가 완료되었습니다.";

        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.info("a_m_no ALREADY EXISTS!!");
            } else {
                e.printStackTrace();
                log.info("a_m_no INSERT FAIL!!");
            }

            // 예외 발생 시 null 또는 예외 메시지 반환
            return "이미지 업로드 중 오류가 발생했습니다.";
        }
    }





    @Override
    @Transactional
    public void createS3(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                // 파일이 존재하면 업로드하고 URL을 얻어옴
                String url = s3Uploader.uploadFileToS3(file, "static/test");
                // 여기서 url을 사용하는 로직 추가
            }
        }
    }






    // 상세페이지 보기
    /*@Override
    public AdminAccmDto showAccmDetail(int a_m_no) {
        log.info("[AdminAccmService] showAccmDetail()");

        AdminAccmDto adminAccmDtos = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);

        String imageUrl = adminAccmDtos.getA_acc_image();
        log.info("[AdminAccmService] imageUrl: " + imageUrl);

        return adminAccmDtos;

    }
*/
    // 수정
    @Override
    public int modifyConfirm(AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmService] modifyConfirm()");

        int result = iAdminAccmDaoMapper.updataeAccmInfo(adminAccmDto);

        if(result>0){
            log.info("[AdminAccmService] MODIFY ACCM SUCCESS!!");

            log.info("[AdminAccmService]: " + adminAccmDto);

            return result;

        } else {
            log.info("[AdminAccmService] MODIFY ACCM FAIL!!");

            return 0;

        }

    }

    // 삭제
    /*@Override
    public int deleteAccm(int a_m_no) {
        log.info("[AdminAccmService] modifyConfirm()");

        int result = iAdminAccmDaoMapper.deleteAccmInfo(a_m_no);

        if(result > 0) {
            log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");

            return result;

        } else {
            log.info("[AdminAccmService] DELETE ACCM FAIL!!");

            return 0;

        }

    }*/




   /* @Override
    public int deleteAccm(int a_m_no) {
        log.info("[AdminAccmService] deleteAccm()");

        AdminAccmDto adminAccmDto = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);
        String imageUrl = adminAccmDto.getA_acc_image();

        // S3에서 이미지 삭제
        s3Uploader.deleteFileFromS3(imageUrl);

        int result = iAdminAccmDaoMapper.deleteAccmInfo(a_m_no);

        if(result > 0) {
            log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");
            return result;
        } else {
            log.info("[AdminAccmService] DELETE ACCM FAIL!!");
            return 0;
        }
    }*/





}