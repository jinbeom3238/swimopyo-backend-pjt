package com.btc.swimpyo.backend.mappers.member.user;

import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMemberDaoMapper {
    int insertMemberUser(UserMemberDto userMemberDto);
}
