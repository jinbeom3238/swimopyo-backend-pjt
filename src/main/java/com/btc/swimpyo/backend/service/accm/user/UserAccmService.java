package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.mappers.accm.user.IUserAccmDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserAccmService implements IUserAccmService{

    private IUserAccmDaoMapper iUserAccmDaoMapper;

    public UserAccmService(IUserAccmDaoMapper iUserAccmDaoMapper) {
        this.iUserAccmDaoMapper = iUserAccmDaoMapper;
    }

    // 리스트 조회
    @Override
    public Map<String, Object> showAccmList(AdminAccmDto adminAccmDto) {
        log.info("[UserAccmService] showAccmList()");

        int a_acc_no;
        Map<String,Object> msgData = new HashMap<>();

        AdminAccmImageDto adminAccmImageDto = new AdminAccmImageDto();
        List<AdminAccmDto> adminAccmDtos;
        List<Integer> a_i_nos = new ArrayList<>();
        List<AdminAccmImageDto> adminImgDtos = new ArrayList<>();

        adminAccmDtos = iUserAccmDaoMapper.selectAccmList(adminAccmDto);
        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDtos);

        // a_acc_no 값 가져오기
        List<Integer> a_acc_nos = iUserAccmDaoMapper.selectAccmNo(adminAccmDto);
        log.info("a_acc_nos: " + a_acc_nos);

        for(int i = 0; i<a_acc_nos.size(); i++) {
            a_acc_no = a_acc_nos.get(i);
            log.info("a_acc_no: " + a_acc_no);

            adminAccmImageDto.setA_acc_no(a_acc_no);

            // a_i_no 가져오기
            a_i_nos = iUserAccmDaoMapper.selectAccmImgNo(a_acc_no);
            log.info("a_i_nos: " + a_i_nos);

            // a_acc_no로 이미지 가져오기
            List<AdminAccmImageDto> adminAccmImagesForAccNo = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);
            adminImgDtos.addAll(adminAccmImagesForAccNo);
            log.info("[UserAccmService] adminImgDtos: " + adminImgDtos);

        }

        msgData.put("adminAccmDtos", adminAccmDtos);
        msgData.put("adminImgDtos", adminImgDtos);
        msgData.put("a_i_nos", a_i_nos);

        log.info("[UserAccmService] msgData: " + msgData);

        return msgData;

    }

    // 상세페이지 보기
    @Override
    public Map<String, Object> showAccmDetail(int a_acc_no) {
        log.info("[UserAccmService] showAccmDetail()");

        Map<String, Object> msgData = new HashMap<>();

        AdminAccmDto adminAccmDto = iUserAccmDaoMapper.selectAccmDetail(a_acc_no);
        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDto);
        log.info("[UserAccmService] a_acc_no: " + a_acc_no);

        // 이미지 정보 가져오기
        List<AdminAccmImageDto> a_i_images = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);
        log.info("[UserAccmService] a_i_images: " + a_i_images);

        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);
        log.info("[UserAccmService] msgData: " + msgData);

        return msgData;

    }


}
