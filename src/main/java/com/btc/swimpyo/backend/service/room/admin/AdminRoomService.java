package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import com.btc.swimpyo.backend.mappers.accm.admin.IAdminAccmDaoMapper;
import com.btc.swimpyo.backend.mappers.room.admin.IAdminRoomDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminRoomService implements IAdminRoomService{

    private final IAdminRoomDaoMapper iAdminRoomDaoMapper;
    private final S3Uploader s3Uploader;

    /*@Autowired
    public AdminAccmService(IAdminAccmDaoMapper iAdminAccmDaoMapper, S3Uploader s3Uploader) {
        this.iAdminAccmDaoMapper = iAdminAccmDaoMapper;
        this.s3Uploader = s3Uploader;
    }*/

    // 등록
    @Override
    public String registConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_images) {
        log.info("[AdminRoomService] registConfirm()");
        try {

            AdminAccmDto adminAccmDto = new AdminAccmDto();

            int a_acc_no = adminAccmDto.getA_acc_no();

            // 1. tbl_admin_room 테이블에 데이터 등록
            int result = iAdminRoomDaoMapper.insertRoomInfo(adminRoomDto);
            log.info("[registConfirm] result : " + result);

            // 2. 등록된 룸의 번호 가져오기
                int a_r_no = iAdminRoomDaoMapper.selectRoomForArNo(adminRoomDto);
                log.info("[selectRoomForArNo] a_r_no : " + a_r_no );

            // AdminRoomImageDto 객체 생성
            AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
            adminRoomImageDto.setA_r_no(a_r_no);

            // 3. tbl_room_image 테이블에 이미지 정보 등록
            for (MultipartFile file : r_i_images) {
                log.info("[for MultipartFile] r_i_image: "+ r_i_images);

                String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");

                adminRoomImageDto.setR_i_image(imageUrl);

                log.info("[AdminRoomService] imageUrl: " + imageUrl);

                iAdminRoomDaoMapper.insertRoomImage(adminRoomImageDto);

                log.info("[insertRoomImage] result : " + result);

            }
            // 4. 이미지 업로드가 완료되면 이미지 URL을 반환
            return "이미지 업로드가 완료되었습니다.";

        } catch (Exception e) {
            e.printStackTrace();
            log.info("숙박시설 등록에 실패하였습니다.");
        }

        // 예외 발생 시 null 또는 예외 메시지 반환
        return "이미지 업로드 중 오류가 발생했습니다.";
    }
}



//        int result = iAdminRoomDaoMapper.insertRoomInfo(adminRoomDto);
//
//        log.info("adminRoomDto: " + adminRoomDto);
//        log.info("adminRoomDto: " + adminRoomDto.getA_acc_no());
//
//
//        if(result > 0) {
//            log.info("[AdminRoomService] ROOM REGIST SUCCESS!!");
//
//            return "성공";
//
//        } else {
//            log.info("[AdminRoomService] ROOM REGIST FAIL!!");
//
//            return "실패";
//
//        }
//
//    }
//}
