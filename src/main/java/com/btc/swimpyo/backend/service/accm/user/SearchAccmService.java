package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.user.ISearchAccmDaoMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class SearchAccmService implements ISearchAccmService {

    private final ISearchAccmDaoMapper iSearchAccmDaoMapper;

    @Override
//    public List<AdminAccmDto> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
    public List<Map<String, Object>> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
        log.info("searchAccm");
        log.info("tp =-----> {}", msgMap);
        String parsingStartDay = msgMap.get("startDay").toString();
        String parsingEndDay = msgMap.get("endDay").toString();
        msgMap.put("startDay", parsingStartDay);
        msgMap.put("endDay", parsingEndDay);
        LocalDate startDay = LocalDate.parse(parsingStartDay);
        LocalDate endDay = LocalDate.parse(parsingEndDay);
        long Days = ChronoUnit.DAYS.between(startDay, endDay);
        msgMap.put("Days", Days);
        if (msgMap.get("priceOrder") != null) {
            msgMap.put("priceOrder", Integer.parseInt(msgMap.get("priceOrder").toString()));
        }
        log.info("msgMap + Days => {}", msgMap);

//        List<AdminAccmDto> selectAccms = iSearchAccmDaoMapper.selectAccms(msgMap);
        List<Map<String, Object>> selectAccms = iSearchAccmDaoMapper.selectAccms(msgMap);

        if (selectAccms != null) {
            for ( Map<String, Object> parserAccmList : selectAccms ){
                if(parserAccmList.get("a_acc_name") != null){
//                    log.info("tp====>=> {}", parserAccmList);
                }

            }

        }


        return selectAccms;

    }
}
