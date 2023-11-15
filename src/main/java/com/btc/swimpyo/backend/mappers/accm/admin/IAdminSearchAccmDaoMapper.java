package com.btc.swimpyo.backend.mappers.accm.admin;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminSearchAccmDaoMapper {


    List<Map<String, Object>> selectRezList(int a_r_no);

    int selectAccmCheck(int a_m_no);
}
