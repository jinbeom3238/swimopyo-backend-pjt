package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
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

        int a_acc_no = adminAccmDto.getA_acc_no();
        Map<String,Object> msgData = new HashMap<>();

        List<AdminAccmDto> adminAccmDtos;

        adminAccmDtos = iUserAccmDaoMapper.selectAccmList(adminAccmDto);
        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDtos);

        // a_acc_no 값 가져오기
        List<Integer> a_acc_nos = iUserAccmDaoMapper.selectAccmNo(adminAccmDto);

        for(int i = 0; i<a_acc_nos.size(); i++) {
            a_acc_no = a_acc_nos.get(i);
            log.info("a_acc_no: " + a_acc_no);

        }

        // a_acc_no로 이미지 가져오기
        List<String> adminImgDtos = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);

        log.info("[UserAccmService] adminImgDtos: " + adminImgDtos);

        msgData.put("adminAccmDtos", adminAccmDtos);
        msgData.put("adminImgDtos", adminImgDtos);

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
        List<String> a_i_images = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);
        log.info("[UserAccmService] a_i_images: " + a_i_images);

        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);
        log.info("[UserAccmService] msgData: " + msgData);

        return msgData;

    }


}
