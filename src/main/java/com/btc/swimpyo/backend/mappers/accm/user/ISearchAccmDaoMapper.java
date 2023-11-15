package com.btc.swimpyo.backend.mappers.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ISearchAccmDaoMapper {
    //    List<AdminAccmDto> selectAccms(Map<String, Object> msgMap);
    List<Map<String, Object>> selectAccms(Map<String, Object> msgMap);

    List<AdminAccmDto> mapInfoList(String region);

    List<Map<String, Object>> selectTop3Accm(String accmValue);
}
