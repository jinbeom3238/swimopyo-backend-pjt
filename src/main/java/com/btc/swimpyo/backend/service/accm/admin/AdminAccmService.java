package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.mappers.accm.admin.IAdminAccmDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public String registConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_images) {
        log.info("[AdminAccmService] registConfirm()");
        log.info("[AdminAccmService] dto : " + adminAccmDto.getA_m_no());
        log.info("[AdminAccmService] a_i_image : " + a_i_images);

        try {
            // 1. tbl_admin_accommodation 테이블에 데이터 등록
            int result = iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);
            log.info("[insertAccmInfo] reuslt: " + result);
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

                result = iAdminAccmDaoMapper.insertAccmImage(adminAccmImageDto);

//                Map<String, Object> msgData = new HashMap<>();
//                msgData.put("registInfo", adminAccmDto);
//                log.info("msgData : " + msgData);

                log.info("[insertAccmImage] result: " + result);

            }
            // 4. 이미지 업로드가 완료되면 이미지 URL을 반환
            return "이미지 업로드가 완료되었습니다.";

        } catch (Exception e) {
            // 중복된 a_m_no가 이미 존재할 때, 해당 예외를 처리함
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.info("숙박시설이 등록되어 있습니다. 확인해주세요. " + e);

            } else {
                e.printStackTrace();
                log.info("숙박시설 등록에 실패하였습니다.");

            }

            // 예외 발생 시 null 또는 예외 메시지 반환
            return "이미지 업로드 중 오류가 발생했습니다.";

        }
    }

    // 상세페이지 보기
    @Override
    public Map<String, Object> showAccmDetail(int a_m_no) {
        log.info("[AdminAccmService] showAccmDetail()");

        Map<String, Object> msgData = new HashMap<>();
        List<String> a_i_images = new ArrayList<>();


        AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();

        // 숙박시설 정보(이미지 제외) 들을 가지고 옴
        AdminAccmDto adminAccmDto = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);
        log.info("adminAccDto : " + adminAccmDto);
        log.info("[AdminAccmService] a_m_no :" + a_m_no);
//        log.info("[AdminAccmService] adminAccmDto.getA_m_no() :" + adminAccmDto.getA_m_no());


        // a_acc_name이 있다면 true
        // 문자열 유효성 체크할 경우. true인데 false로 반환하는 경우가 있음.
        // StringUtils.hasText => 공백, null, 길이 0일 때 false값을 주므로 오류 발생률을 줄여줌
        if (StringUtils.hasText(adminAccmDto.getA_acc_name())) {
            
            // a_acc_no를 가져와서 image를 보여줌

            int a_acc_no = iAdminAccmDaoMapper.selectAccmForAmNo(a_m_no);

            log.info("[AdminAccmService] a_acc_no: " + a_acc_no);

            if (a_acc_no > 0) {
                log.info("selectAccmForAmNo success!!");

                log.info("[AdminAccmService] a_acc_no: " + a_acc_no);


                // 숙박시설 이미지 받아오기
                List<Integer> a_i_nos = iAdminAccmDaoMapper.selectAccmImgNo(a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_i_nos : " + a_i_nos);
//                adminAccmDto.getA_i_nos(a_i_nos);

                a_i_images = iAdminAccmDaoMapper.selectAccmImg(a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_acc_no : " + a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_m_no : " + a_m_no);
                log.info("[AdminAccmService] a_i_images: " + a_i_images);

            }

        }
        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);

        return msgData;

    }

    @Override
    public String modifyConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_image, List<String> deleteImgs) {
        log.info("[AdminAccmService] modifyConfirm()");

        List<String> a_i_images = new ArrayList<>();
        AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();
        int a_acc_no = adminAccmDto.getA_acc_no();
        log.info("a_acc_no: " + a_acc_no);
        adminAccmImageDto.setA_acc_no(a_acc_no);

        log.info("[AdminAccmService] adminAccmDto.getA_acc_address() : " + adminAccmDto.getA_acc_address());
        log.info("[AdminAccmService] adminAccmDto.getA_acc_name() : " + adminAccmDto.getA_acc_name());
        log.info("[AdminAccmService]  a_m_no.getA_m_no() : " + adminAccmDto.getA_m_no());
        log.info("[AdminAccmService]  a_m_no.getA_acc_no() : " + adminAccmDto.getA_acc_no());

        int result = iAdminAccmDaoMapper.updateAccmInfo(adminAccmDto);
        log.info("[AdminAccmService] updateAccmInfo()");
        log.info("result : " + result);

        // update가 되면
        if (result > 0) {
            log.info("[AdminAccmService] updateAccmInfo() SUCCESS!! GOGO UPDATE IMAGE~!!");
//            Map<String, Object> msgData = new HashMap<>();
//            msgData.put("a_i_image", a_i_image);
//            msgData.put("a_acc_no", a_acc_no);

            // 새로운 이미지 추가(Insert)
            if (a_i_image != null) {
                // 새로운 사진 업데이트(추가) 전 삭제해주는 작업
//                int isDelete = iAdminAccmDaoMapper.deleteAccmImg(a_acc_no);
                int isDelete = iAdminAccmDaoMapper.deleteAccmImgs(deleteImgs);

                if (isDelete > 0) {
                    log.info("deleteAccmImgs() SUCCESS!! ");

                    for (MultipartFile file : a_i_image) {

                        log.info("[AdminAccmService] a_i_image: -----> {}", file);

                        String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");

                        adminAccmImageDto.setA_i_image(imageUrl);

                        log.info("[AdminAccmService] imageUrl: " + imageUrl);

                        // 필요없는 사진 삭제 후 새로운 사진 등록
                        int isInsert = iAdminAccmDaoMapper.insertAccmImage(adminAccmImageDto);
                        log.info("isInsert: " + isInsert);

                    }

                }

                log.info("[AdminAccmService] MODIFY ACCM SUCCESS!!");
                log.info("[AdminAccmService]: " + adminAccmDto);


            }

            return "숙박시설 정보 수정 완료";

        }

        return "숙박시설 정보 수정 실패";

    }



    @Override
    public int deleteAccm(int a_m_no) {
        log.info("[AdminAccmService] deleteAccm()");

        AdminAccmDto adminAccmDto = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);
//        String imageUrl = adminAccmDto.getA_i_image();
        int a_acc_no = adminAccmDto.getA_acc_no();

        // S3에서 이미지 삭제
//        s3Uploader.deleteFileFromS3(imageUrl);

        // 이미지를 제외한 숙박시설 정보 삭제(UPDATE)
        int result = iAdminAccmDaoMapper.deleteAccmInfo(a_m_no);

        if(result > 0) {
            log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");

            iAdminAccmDaoMapper.deleteAccmImg(a_acc_no);

            return result;

        } else {
            log.info("[AdminAccmService] DELETE ACCM FAIL!!");
            return 0;

        }
    }

}