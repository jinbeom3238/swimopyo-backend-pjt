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
//         adminAccmDtos;
        List<Integer> a_i_nos = new ArrayList<>();
        List<AdminAccmImageDto> adminImgDtos = new ArrayList<>();

        List<AdminAccmDto> adminAccmDtos = iUserAccmDaoMapper.selectAccmList(adminAccmDto);
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
        // 별점 가져오기
        Double sa_point_avg = iUserAccmDaoMapper.selectReviewStar(a_acc_no);
        adminAccmDto.setSa_point_avg(sa_point_avg);
        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDto);
        log.info("[UserAccmService] a_acc_no: " + a_acc_no);
        log.info("[UserAccmService] r_sa_point: " + adminAccmDto.getSa_point_avg());

        // 이미지 정보 가져오기
        List<AdminAccmImageDto> a_i_images = iUserAccmDaoMapper.selectAccmImgList(a_acc_no);
        log.info("[UserAccmService] a_i_images: " + a_i_images);

        // 경도, 위도 값을 가지고 오기 위한 a_acc_address
        String address = adminAccmDto.getA_acc_address();
        log.info("[AdminAccmController] Address: " + address);

        // kakao - getKakaoApiFromAddress매서드를 통해 a_acc_address에 관한 정보 값들을 String 형태로 받아옴
        String jsonString = kakaoMapApiController.getKakaoApiFromAddress(address);
//        log.info("jsonString: " + jsonString);

        // JSON String -> Map
        ObjectMapper mapper = new ObjectMapper();
        // Map<String, Object> 형태의 데이터를 읽고, TypeReference를 사용하여 이 정보를 ObjectMapper에게 전달
        // 즉, JSON을 어떤 형태의 Java 객체로 변환할지 결정
        TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>(){};
        // mapper 객체를 사용하여 JSON 문자열 jsonString을 읽고, typeRef에 지정된 형태로 맵(Map) 형식의 자바 객체로 변환
        Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);

        log.info("jsonMap: " + jsonMap);

        List<LinkedHashMap<String, Object>> document = (List<LinkedHashMap<String, Object>>) jsonMap.get("documents");

//        log.info("docment: " + document.get(0).get("address"));

        // document 내의 {} 안에 있는 값들이 배열이 아니라 하나의 String 값으로 되어 있었던 것. 
        // 따라서, split매서드를 통해 "," 문자열을 기준으로 하나씩 뽑아옴
        String [] list = String.valueOf(document.get(0).get("address")).split(",");
        for(int i =10; i< list.length; i++) {
            log.info("list " + i + "" + list[i]);
        }

//        log.info("x : " + list[10].substring(3, list[10].length()));
//        log.info("x : " + list[11].substring(3, list[11].length()-1));

        // substring을 통해 x=~~으로 담겨있던 값들을 x=을 제외한 경도, 위도 값만 가지고 올 수 있었음
        // 위도, 경도 값을 adminAccmDto에 담아줌 -> 이후 Map 형태로 front에 전달
        String a_acc_longitude = list[10].substring(3, list[10].length());
        String a_acc_latitude = list[11].substring(3, list[11].length()-1);
        adminAccmDto.setA_acc_longitude(a_acc_longitude);
        adminAccmDto.setA_acc_latitude(a_acc_latitude);

        log.info("x : " + adminAccmDto.getA_acc_longitude());
        log.info("y : " + adminAccmDto.getA_acc_latitude());

//        log.info("jsonMap:---------->" + jsonMap.get("documents"));

        // db에 넣어줘야 함
        iUserAccmDaoMapper.insertAccmLoc(adminAccmDto);

        msgData.put("adminAccmDto", adminAccmDto);
        msgData.put("a_i_images", a_i_images);
        log.info("[UserAccmService] msgData: " + msgData);

        return msgData;

    }


}
