package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.controller.api.KakaoMapApiController;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.dto.accm.admin.XyDto;
import com.btc.swimpyo.backend.mappers.accm.user.IUserAccmDaoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserAccmService implements IUserAccmService{

    private final IUserAccmDaoMapper iUserAccmDaoMapper;
    private final KakaoMapApiController kakaoMapApiController;


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
    public Map<String, Object> showAccmDetail(int a_acc_no) throws JsonProcessingException {
        log.info("[UserAccmService] showAccmDetail()");

        Map<String, Object> msgData = new HashMap<>();

        AdminAccmDto adminAccmDto = iUserAccmDaoMapper.selectAccmDetail(a_acc_no);
        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDto);
        log.info("[UserAccmService] a_acc_no: " + a_acc_no);

        // 이미지 정보 가져오기
        List<AdminAccmImageDto> a_i_images = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);
        log.info("[UserAccmService] a_i_images: " + a_i_images);

        String address = adminAccmDto.getA_acc_address();
        log.info("[AdminAccmController] Address: " + address);

        String jsonString = kakaoMapApiController.getKakaoApiFromAddress(address);
        log.info("value: " + jsonString);

        // JSON String -> Map
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){};

        Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);

        List<LinkedHashMap<String, Object>> document = (List<LinkedHashMap<String, Object>>) jsonMap.get("documents");

        log.info("docment: " + document.get(0).get("address"));

//        StringTokenizer xySplit = new StringTokenizer(String.valueOf(document.get(0).get("address")), ",");
//
//        while (xySplit.hasMoreTokens()){
//
//            log.info("xySplit:" + xySplit.nextToken());
//
//        }

        String [] list = String.valueOf(document.get(0).get("address")).split(",");
        for(int i =10; i< list.length; i++) {
            log.info("list " + i + "" + list[i]);
        }

//        log.info("x : " + list[10].substring(3, list[10].length()));
//        log.info("x : " + list[11].substring(3, list[11].length()-1));

        String a_acc_longitude = list[10].substring(3, list[10].length());
        String a_acc_latitude = list[11].substring(3, list[11].length()-1);
        adminAccmDto.setA_acc_longitude(a_acc_longitude);
        adminAccmDto.setA_acc_latitude(a_acc_latitude);

        log.info("x : " + adminAccmDto.getA_acc_longitude());
        log.info("y : " + adminAccmDto.getA_acc_latitude());


        log.info("jsonMap:---------->" + jsonMap.get("documents"));

//        String key = "AIzaSyAULzYweWdEST8X8YqvXhwCDRiCEAMNIUw";

        // 경도, 위도
//        Map<String, Object> coords = GeoCoderController.geoCoding(address, key);

        // db에 넣어줘야 함
        iUserAccmDaoMapper.insertAccmLoc(adminAccmDto);

        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);
//        msgData.put("coords", coords);
        log.info("[UserAccmService] msgData: " + msgData);

        return msgData;

    }


}
