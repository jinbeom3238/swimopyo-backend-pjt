//package com.btc.swimpyo.backend.service.accm.user;
//
//import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
//import com.btc.swimpyo.backend.mappers.accm.user.ISearchAccmDaoMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class SearchAccmService implements ISearchAccmService {
//
//    private final ISearchAccmDaoMapper iSearchAccmDaoMapper;
//
//    @Override
////    public List<AdminAccmDto> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
//    public List<Map<String, Object>> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
//        log.info("searchAccm");
//        log.info("tp =-----> {}", msgMap);
//        String parsingStartDay = msgMap.get("startDay").toString();
//        String parsingEndDay = msgMap.get("endDay").toString();
//        msgMap.put("startDay", parsingStartDay);
//        msgMap.put("endDay", parsingEndDay);
//        LocalDate startDay = LocalDate.parse(parsingStartDay);
//        LocalDate endDay = LocalDate.parse(parsingEndDay);
//        long Days = ChronoUnit.DAYS.between(startDay, endDay);
//        msgMap.put("Days", Days);
//        if (msgMap.get("priceOrder") != null) {
//            msgMap.put("priceOrder", Integer.parseInt(msgMap.get("priceOrder").toString()));
//        }
//        log.info("msgMap + Days => {}", msgMap);
//
////        List<AdminAccmDto> selectAccms = iSearchAccmDaoMapper.selectAccms(msgMap);
//        List<Map<String, Object>> selectAccms = iSearchAccmDaoMapper.selectAccms(msgMap);
//
//        if (selectAccms != null) {
//            Map<String, Map<String, Object>> result = new HashMap<>();
//            for (Map<String, Object> hotel : selectAccms) {
//                String accName = (String) hotel.get("a_acc_name");
//                if (result.containsKey(accName)) {
//                    int existingPrice = (int) result.get(accName).get("a_r_price");
//                    int additionalPrice = (int) hotel.get("a_r_price");
//                    result.get(accName).put("a_r_price", existingPrice + additionalPrice);
//                } else {
//                    result.put(accName, new HashMap<>(hotel));
//                }
//            }
//            return new ArrayList<>(result.values());
//
//
//        }
//
//
//
//
//        return selectAccms;
//
//    }
//}

package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.user.ISearchAccmDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SearchAccmService implements ISearchAccmService {

    private final ISearchAccmDaoMapper iSearchAccmDaoMapper;

    @Override
    public List<Map<String, Object>> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
        log.info("searchAccm - tp =-----> {}", msgMap);

        // 날짜 파싱 및 일수 계산
        LocalDate startDay = LocalDate.parse(msgMap.get("startDay").toString());
        LocalDate endDay = LocalDate.parse(msgMap.get("endDay").toString());
        msgMap.put("Days", ChronoUnit.DAYS.between(startDay, endDay));

        // priceOrder가 null이 아닐 경우 파싱
        if (msgMap.get("priceOrder") != null) {
            msgMap.put("priceOrder", Integer.parseInt(msgMap.get("priceOrder").toString()));
        }

        log.info("msgMap + Days => {}", msgMap);

        // 숙박 시설 데이터 검색
        List<Map<String, Object>> selectAccms = iSearchAccmDaoMapper.selectAccms(msgMap);
        log.info("selectAccms => {}", selectAccms);

        if (selectAccms == null) {
            return List.of(); // 검색 결과가 없을 경우 빈 리스트 반환
        }

        // Days가 2 이상일 때
        if (selectAccms != null && Integer.parseInt(msgMap.get("Days").toString()) > 1) {
            // 동일한 이름을 가진 숙박 시설의 가격을 합산
            return new ArrayList<>(selectAccms.stream()
                    .collect(Collectors.toMap(
                            accm -> (String) accm.get("a_acc_name"), // 키로 사용할 숙박 시설 이름
                            Function.identity(), // 값으로 사용할 원래 객체
                            (accm1, accm2) -> { // 동일한 키를 가진 객체들을 병합하는 로직
                                accm1.put("a_r_price", (int) accm1.get("a_r_price") + (int) accm2.get("a_r_price"));
                                return accm1; // 가격을 합산한 객체 반환
                            }))
                    .values()); // Map의 값들을 리스트로 변환

        }

        // Days가 1 일 때
        return new ArrayList<>(selectAccms.stream()
                .collect(Collectors.toMap(
                        accm -> (String) accm.get("a_acc_name"), // 키로 사용할 숙박 시설 이름
                        Function.identity(), // 값으로 사용할 원래 객체
                        (accm1, accm2) -> accm1 // 동일한 키를 가진 객체가 여러 개 있을 경우, 첫 번째 객체를 유지
                ))
                .values()); // Map의 값들을 리스트로 변환
    }
}
