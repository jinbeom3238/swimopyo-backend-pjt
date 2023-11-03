package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.controller.api.GeoCoderController;
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

        // 숙박시설 정보(이미지 제외) 들을 가지고 옴
        AdminAccmDto adminAccmDto = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);
        log.info("adminAccDto : " + adminAccmDto);
        log.info("[AdminAccmService] a_m_no :" + a_m_no);
//        log.info("[AdminAccmService] adminAccmDto.getA_m_no() :" + adminAccmDto.getA_m_no());
        List<Integer> a_i_nos = adminAccmDto.getA_i_nos();


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
                // 숙박시설 a_i_no front에 보내주기 위해 추가
                a_i_nos = iAdminAccmDaoMapper.selectAccmImgNo(a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_i_nos : " + a_i_nos);

                // 위에서 받은 a_i_no를 통해 a_i_image를 받아옴
                a_i_images = iAdminAccmDaoMapper.selectAccmImg(a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_acc_no : " + a_acc_no);
                log.info("[AdminAccmService] [selectAccmImg] a_m_no : " + a_m_no);
                log.info("[AdminAccmService] a_i_images: " + a_i_images);

            }

        }
        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);
        msgData.put("a_i_nos", a_i_nos);

        return msgData;

    }

    // 수정
    @Override
    public String modifyConfirm(AdminAccmDto adminAccmDto, MultipartFile[] a_i_image, List<Integer> deleteImgs) {
        log.info("[AdminAccmService] modifyConfirm()");

//        String key = "img/my-img.png";

        List<String> images = new ArrayList<>();
        AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();
        int a_acc_no = adminAccmDto.getA_acc_no();
        log.info("a_acc_no: " + a_acc_no);
        adminAccmImageDto.setA_acc_no(a_acc_no);

        log.info("[AdminAccmService]  a_m_no.getA_m_no() : " + adminAccmDto.getA_m_no());

        // 이미지를 제외한 숙박시설 정보 update
        int result = iAdminAccmDaoMapper.updateAccmInfo(adminAccmDto);
        log.info("[AdminAccmService] updateAccmInfo()");
        log.info("result : " + result);

        // update가 되면
//        if (result > 0) {
        log.info("[AdminAccmService] updateAccmInfo() SUCCESS!! GOGO IMAGE UPDATE~!!");

        // 기존이미지를 삭제시킨다면
        if(deleteImgs != null) {
            log.info("deleteImgs NOT NULL!!");

            // deleteImgs가 integer이므로 String imageUrl에 담아주기 위해 deleteImgFile 리스트로 담아오기
            // List<String> deleteImgFile = iAdminAccmDaoMapper.selectAccmImg(a_acc_no);

            // s3 삭제
                /*for (String imageUrl : deleteImgs) {
                    s3Uploader.deleteFileFromS3(imageUrl);

                }*/

            // s3 & db 삭제
            // deleteImgs가 List로 담기므로 이미지 하나씩 삭제해주기 위해 for문 사용
            for(int i = 0; i < deleteImgs.size(); i++) {
                adminAccmDto.setA_i_no(deleteImgs.get(i));
                int deleteNo = adminAccmDto.getA_i_no();
                log.info("a_i_no: " + deleteNo);

                // front에서 넘어온 삭제할 a_i_no 리스트들에 대한 image 값들을 들고 오기 위함
                List<String> deleteImg = iAdminAccmDaoMapper.selectAccmImgs(deleteNo);
                log.info("deleteImg: " + deleteImg);

                // s3 삭제
                // deleteImg 리스트에 있는 모든 이미지 파일을 S3에서 삭제
                for (String imageUrl : deleteImg) {
                    s3Uploader.deleteFileFromS3(imageUrl);

                }

                // deleteNo를 통해 기존 이미지 삭제
                int isdelete = iAdminAccmDaoMapper.deleteAccmdelImgs(deleteNo);
                log.info("[selectAccmImgForDelete] isdelete-----> {}", isdelete);

            }

            // 기존 이미지를 삭제하고 추가할 이미지가 있는 경우
            if(a_i_image != null) {
                log.info("a_i_image NOT NULL!!");

                // s3 파일 업로드
                for (MultipartFile file : a_i_image) {
                    log.info("[AdminAccmService] a_i_image: -----> {}", file);
//                            String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");
                    String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");
                    adminAccmImageDto.setA_i_image(imageUrl);
                    log.info("[AdminAccmService] imageUrl: " + imageUrl);

                    // 새로운 사진 등록
                    int isInsert = iAdminAccmDaoMapper.insertAccmImage(adminAccmImageDto);
                    log.info("isInsert: " + isInsert);
                }

            }

        } else if(deleteImgs == null) {         // 기존이미지를 유지한다면(삭제할 이미지가 없는 경우)
            log.info("deleteImgs NULL!!");

            // 이미지를 추가하지 않고, 기존 이미지 그대로 update하는 경우(삭제할 이미지, 추가할 이미지 모두 없는 경우)
            iAdminAccmDaoMapper.selectAccmImg(a_acc_no);

            // 기존 이미지는 그대로, 새로운 이미지 파일만 추가하는 경우
            if(a_i_image != null) {
                // s3 파일 업로드
                for (MultipartFile file : a_i_image) {
                    log.info("[AdminAccmService] a_i_image: -----> {}", file);
                    String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");
                    adminAccmImageDto.setA_i_image(imageUrl);
                    log.info("[AdminAccmService] imageUrl: " + imageUrl);

                    // 새로운 사진 등록
                    int isInsert = iAdminAccmDaoMapper.insertAccmImage(adminAccmImageDto);
                    log.info("isInsert: " + isInsert);
                }
            }
        }

        log.info("[AdminAccmService] MODIFY ACCM SUCCESS!!");
        log.info("[AdminAccmService]: " + adminAccmDto);

        return "숙박시설 정보 수정 완료";

    }

    @Override
    public int deleteAccm(int a_m_no) {
        log.info("[AdminAccmService] deleteAccm()");

        // 숙박시설 정보 조회(이미지 제외)
        AdminAccmDto adminAccmDto = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);
//        String imageUrl = adminAccmDto.getA_i_image();
        int a_acc_no = adminAccmDto.getA_acc_no();

        // 이미지를 제외한 숙박시설 정보 삭제(UPDATE)
        iAdminAccmDaoMapper.deleteAccmInfo(a_m_no);

        // 숙박시설 이미지 조회
        List<String> deleteImg = iAdminAccmDaoMapper.selectAccmImg(a_acc_no);

        // S3에서 이미지 삭제
        // s3 삭제
        // deleteImg 리스트에 있는 모든 이미지 파일을 S3에서 삭제
        for (String imageUrl : deleteImg) {
            s3Uploader.deleteFileFromS3(imageUrl);

        }
        log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");

        int result = iAdminAccmDaoMapper.deleteAccmImg(a_acc_no);

        return result;

    }

}