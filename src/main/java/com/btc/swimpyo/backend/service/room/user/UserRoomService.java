package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import com.btc.swimpyo.backend.dto.room.user.UserRoomDto;
import com.btc.swimpyo.backend.mappers.room.user.IUserRoomDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserRoomService implements IUserRoomService{

    private final IUserRoomDaoMapper iUserRoomDaoMapper;
    private final S3Uploader s3Uploader;

    // 룸 리스트 조회
    @Override
    public Map<String, Object> showRoomList(int a_acc_no) {
        log.info("[UserRoomService] showRoomList()");

        AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
        AdminRoomDto adminRoomDto = new AdminRoomDto();
        Map<String, Object> msgData = new HashMap<>();

        List<AdminRoomDto> userRoomDtos;
        List<AdminRoomImageDto> r_i_images = new ArrayList<>();
        int a_r_no = adminRoomDto.getA_r_no();

        // 룸 정보 조회(이미지 제외)
        userRoomDtos = iUserRoomDaoMapper.selectRoomInfoForList(a_acc_no);
        log.info("userRoomDto: " + userRoomDtos);

        // a_r_no 가져오기
        List<Integer> a_r_nos = userRoomDtos.stream()
                .map(AdminRoomDto::getA_r_no)
                .collect(Collectors.toList());

        log.info("a_r_nos: " + a_r_nos.get(0));

        for(int i = 0; i<a_r_nos.size(); i++) {
            a_r_no = a_r_nos.get(i);
            log.info("a_r_no: " + a_r_no);

            adminRoomImageDto.setA_r_no(a_r_no);

            // r_i_no 값 가져오기
            List<Integer> r_i_nos = iUserRoomDaoMapper.selectRoomImgNo(a_r_no);
            log.info("r_i_nos: " + r_i_nos);

            List<AdminRoomImageDto> r_i_imagesForArNo = iUserRoomDaoMapper.selectRoomImgForList(a_r_no);
            r_i_images.addAll(r_i_imagesForArNo);

        }

        // 이미지 가져오기
        log.info("r_i_images: " + r_i_images);

        msgData.put("userRoomDtos", userRoomDtos);
        msgData.put("r_i_images", r_i_images);

        return msgData;

    }

    // 룸 정보 조회
    @Override
    public Map<String, Object> showRoomDetail(int a_r_no) {
        log.info("[UserRoomService] showRoomDetail()");

        Map<String, Object> msgData = new HashMap<>();
        AdminRoomImageDto adminRoomImageDto = new AdminRoomImageDto();
//        List<String> r_i_images = new ArrayList<>();

//        AdminRoomDto adminRoomDto;
//        int r_i_no;

        // Room 정보 조회(이미지 제외)
        AdminRoomDto adminRoomDto = iUserRoomDaoMapper.selectRoomInfo(a_r_no);
        log.info("[showRoomDetail] adminRoomDto: " + adminRoomDto);

        log.info("a_r_no: " + a_r_no);
        adminRoomImageDto.setA_r_no(a_r_no);

        // front에 r_i_no 보내주기
        List<Integer> r_i_nos = iUserRoomDaoMapper.selectRoomImgNo(a_r_no);
        log.info("r_i_nos: " + r_i_nos);

        List<AdminRoomImageDto> r_i_images = iUserRoomDaoMapper.selectRoomImg(a_r_no);
        log.info("r_i_images: " + r_i_images);

        msgData.put("adminRoomDto", adminRoomDto);
        msgData.put("r_i_nos", r_i_nos);
        msgData.put("r_i_images", r_i_images);

        log.info("msgData: " + msgData);

        return msgData;

    }

}