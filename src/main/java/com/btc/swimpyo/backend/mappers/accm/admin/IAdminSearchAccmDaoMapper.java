package com.btc.swimpyo.backend.mappers.accm.admin;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminSearchAccmDaoMapper {

    List<Map<String, Object>> selectRezList(Map<String, Object> msgMap);

    int selectAccmCheck(int a_m_no);
}
