package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.user.ISearchAccmDaoMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class SearchAccmService implements ISearchAccmService{

    private final ISearchAccmDaoMapper iSearchAccmDaoMapper;

    @Override
    public List<AdminAccmDto> searchAccm(Map<String, Object> msgMap, AdminAccmDto adminAccmDto) {
        log.info("searchAccm");
        log.info("msgMap ==> {}", msgMap);
        String searchWord = msgMap.get("searchAccm").toString();

        List<AdminAccmDto> selectAccms = iSearchAccmDaoMapper.selectAccms(searchWord);
        log.info("selectAccms ==> {}", selectAccms);

        return selectAccms;
    }
}
