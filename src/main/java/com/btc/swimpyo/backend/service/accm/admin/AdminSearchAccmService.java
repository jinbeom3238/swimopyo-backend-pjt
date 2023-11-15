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

package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.mappers.accm.admin.IAdminSearchAccmDaoMapper;
import com.btc.swimpyo.backend.utils.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminSearchAccmService implements IAdminSearchAccmService {

    @Value("${secret-key}")
    private String secretKey;

    private final IAdminSearchAccmDaoMapper iAdminSearchAccmDaoMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public List<Map<String, Object>> rezList(int a_m_no) {
        log.info("rezList");

        List<Map<String, Object>> map = iAdminSearchAccmDaoMapper.selectRezList(a_m_no);

        return map;
    }

    @Override
    public int checkAccm(int a_m_no) {
        log.info("checkAccm");

        return iAdminSearchAccmDaoMapper.selectAccmCheck(a_m_no);
    }
}
