package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import com.btc.swimpyo.backend.mappers.room.admin.IAdminRoomDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminRoomService implements IAdminRoomService {

    private final IAdminRoomDaoMapper iAdminRoomDaoMapper;
    private final S3Uploader s3Uploader;

    /*@Autowired
    public AdminAccmService(IAdminAccmDaoMapper iAdminAccmDaoMapper, S3Uploader s3Uploader) {
        this.iAdminAccmDaoMapper = iAdminAccmDaoMapper;
        this.s3Uploader = s3Uploader;
    }*/

    /*
     * 등록
     */
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
            log.info("[selectRoomForArNo] a_r_no : " + a_r_no);

            // AdminRoomImageDto 객체 생성
            AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
            adminRoomImageDto.setA_r_no(a_r_no);

            // 3. tbl_room_image 테이블에 이미지 정보 등록
            for (MultipartFile file : r_i_images) {
                log.info("[for MultipartFile] r_i_image: " + r_i_images);

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

    /*
     * 상세 페이지 조회
     */
    @Override
    public Map<String, Object> showRoomDetail(int a_m_no) {
        log.info("[AdminRoomService] showRoomDetail()");

        Map<String, Object> msgData = new HashMap<>();
        List<String> r_i_images = new ArrayList<>();

        // Room 정보(이미지x)들을 가지고 옴
        AdminRoomDto adminRoomDto = iAdminRoomDaoMapper.selectRoomInfo(a_m_no);
        log.info("[selectRoomInfo] adminRoomDto: " + adminRoomDto);
        log.info("[selectRoomInfo] a_m_no: " + a_m_no);
        List<Integer> r_i_nos = adminRoomDto.getR_i_nos();

        if (StringUtils.hasText(adminRoomDto.getA_r_name())) {

            // 룸 번호(a_r_no)를 가지고 옴
            int a_r_no = iAdminRoomDaoMapper.selectRoomForArNo(adminRoomDto);
            log.info("[selectRoomForArNo] a_r_no: " + a_r_no);

            if (a_r_no > 0) {
                log.info("selectRoomForArNo SUCCESS!!");

                // front에 r_i_no 보내주기
                r_i_nos = iAdminRoomDaoMapper.selectRoomImgNo(a_r_no);
                log.info("[selectRoomImg] r_i_nos: " + r_i_nos);

                // 위에서 받은 r_i_no를 통해 Room 이미지 받아오기
                r_i_images = iAdminRoomDaoMapper.selectRoomImg(a_r_no);
                log.info("[selectRoomImg] a_r_no: " + a_r_no);
                log.info("[selectRoomImg] a_m_no: " + a_m_no);
                log.info("[selectRoomImg] r_i_images: " + r_i_images);

            }

        }
        msgData.put("adminRoomDto", adminRoomDto);
        msgData.put("r_i_images", r_i_images);
        msgData.put("r_i_nos", r_i_nos);

        return msgData;

    }

    /*
     * 수정
     */
    @Override
    public String modifyConfirm(AdminRoomDto adminRoomDto, MultipartFile[] r_i_image, List<Integer> deleteNos) {
        log.info("[AdminRoomService] modifyConfirm()");

        AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
        int a_r_no = adminRoomDto.getA_r_no();
        log.info("a_r_no: {}", a_r_no);
        adminRoomImageDto.setA_r_no(a_r_no);

        log.info("[AdminRoomService]  adminRoomDto.getA_m_no : {}", adminRoomDto.getA_m_no());

        // 이미지를 제외한 숙박시설 정보 update
        int result = iAdminRoomDaoMapper.updateRoomInfo(adminRoomDto);
        log.info("숙박시설(이미지X) UPDATE 성공!! 이미지 업데이트 하자 .......");
        log.info("result :" + result);

        //기존 이미지를 삭제시킨다면
        if (deleteNos != null) {
            log.info("[deleteNos] 기존 이미지 삭제 시작");

            // S3 & DB 삭제
            // deleteNos List로 담기므로 이미지 하나씩 삭제해주기 위해 for문 사용
            for (int i = 0; i < deleteNos.size(); i++) {
                adminRoomDto.setR_i_no(deleteNos.get(i));
                int deleteNo = adminRoomDto.getR_i_no();
                log.info("삭제 요청을 받은 r_i_no: {}", deleteNo);

                // front에서 요청받은 r_i_no 리스트들에 대한 image 값들을 들고 오기 위함
                List<String> deleteImg = iAdminRoomDaoMapper.selectRoomImgs(deleteNo);
                log.info("deleteImg: " + deleteImg);

                // S3 삭제
                // deleteImg 리스트에 있는 모든 이미지 파일을 S3에서 삭제
                for (String imageUrl : deleteImg) {
                    s3Uploader.deleteFileFromS3(imageUrl);

                }

                // deleteNo를 통해 기존 이미지 삭제
                int isdelete = iAdminRoomDaoMapper.deleteRoomImgs(deleteNo);
                log.info("[selectAccmImgForDelete] isdelete-----> {}", isdelete);

            }

            // 기존 이미지를 삭제하고 추가할 이미지가 있는 경우
            if (r_i_image != null) {
                log.info("기존 이미지 삭제 시 새로운 이미지 추가 시작");

                // S3 업로드
                for (MultipartFile file : r_i_image) {
                    log.info("[AdminRoomService] r_i_image의 file :{}", file);
                    String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");

                    adminRoomImageDto.setR_i_image(imageUrl);
                    log.info("[AdminRoomService] imageUrl :{}", imageUrl);

                    // 새로운 사진 등록
                    int isInsert = iAdminRoomDaoMapper.insertRoomImage(adminRoomImageDto);
                    log.info("새로운 사진 등록 isInsert: " + isInsert);

                }

            }

        } else if (deleteNos == null) {   // 기존이미지를 유지한다면(삭제할 이미지가 없는 경우)
            log.info("[deleteNos] 기존 이미지를 삭제하지 않는 경우");

            // 이미지를 추가하지 않고, 기존 이미지 그대로 update하는 경우(삭제할 이미지, 추가할 이미지 모두 없는 경우)
            iAdminRoomDaoMapper.selectRoomImg(a_r_no);

            // 기존 이미지는 그대로, 새로운 이미지 파일만 추가하는 경우
            if (r_i_image != null) {

                // S3 업로드
                for (MultipartFile file : r_i_image) {
                    log.info("[AdminRoomService] r_i_image의 file :{}", file);
                    String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");

                    adminRoomImageDto.setR_i_image(imageUrl);
                    log.info("[AdminRoomService] imageUrl :{}", imageUrl);

                    // 새로운 사진 등록
                    int isInsert = iAdminRoomDaoMapper.insertRoomImage(adminRoomImageDto);
                    log.info("새로운 사진 등록 isInsert: " + isInsert);

                }

            }

        }

        log.info("[AdminRoomService] MODIFY ACCM SUCCESS!!");
        log.info("[AdminRoomService] adminRoomDto: " + adminRoomDto);

        return "숙박시설 정보 수정 완료";

    }

    /*
     * 삭제
     */
    @Override
    public int deleteConfirm(int a_m_no) {
        log.info("[AdminRoomService] deleteConfirm() ");

        // 숙박시설 정보 조회(이미지 제외)
        AdminRoomDto adminRoomDto = iAdminRoomDaoMapper.selectRoomInfo(a_m_no);
        int a_r_no = adminRoomDto.getA_r_no();

        // 이미지를 제외한 숙박시설 정보 삭제(UPDATE)
        iAdminRoomDaoMapper.deleteRoomInfo(a_m_no);

        // Room 이미지 조회
        List<String> deleteImg = iAdminRoomDaoMapper.selectRoomImg(a_r_no);

        // S3에서 이미지 삭제
        // s3 삭제
        // deleteImg 리스트에 있는 모든 이미지 파일을 S3에서 삭제
        for (String imageUrl : deleteImg) {
            s3Uploader.deleteFileFromS3(imageUrl);

        }

        log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");

        // DB에서 이미지 삭제
        int result = iAdminRoomDaoMapper.deleteRoomImg(a_r_no);

        return result;
    }
}
