package com.btc.swimpyo.backend.mappers.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserAccmDaoMapper {
    
    // 리스트 조회
    public List<AdminAccmDto> selectAccmList(AdminAccmDto adminAccmDto);

    public AdminAccmDto selectAccmDetail(int a_acc_no);
}
