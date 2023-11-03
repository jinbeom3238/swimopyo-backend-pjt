package com.btc.swimpyo.backend.mappers.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISearchAccmDaoMapper {
    List<AdminAccmDto> selectAccms(String searchWord);
}
