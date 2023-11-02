package com.btc.swimpyo.backend.mappers.member.user;

import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMemberDaoMapper {
    UserMemberDto isMemberUser(UserMemberDto userMemberDto);
    int insertMemberUser(UserMemberDto userMemberDto);

    RefTokenEntity selectRefToken(RefTokenEntity refTokenEntity);

    int deleteDupRefToken(RefTokenEntity checkedRefToken);

    int insertRefToken(RefTokenEntity refTokenEntity);
}
