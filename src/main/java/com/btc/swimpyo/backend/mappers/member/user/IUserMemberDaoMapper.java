package com.btc.swimpyo.backend.mappers.member.user;

import com.btc.swimpyo.backend.dto.member.user.UserMemberDto;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMemberDaoMapper {
    UserMemberDto isMemberUser(UserMemberDto userMemberDto);
    int insertMemberUser(UserMemberDto userMemberDto);

    RefTokenEntity selectRefToken(RefTokenEntity refTokenEntity);

    int deleteDupRefToken(RefTokenEntity checkedRefToken);

    int insertRefToken(RefTokenEntity refTokenEntity);

    int updateUser(UserMemberDto userMemberDto);

    int updateUserForPw(UserMemberDto userMemberDto);

    String logout(HttpServletRequest request, HttpServletResponse response, RefTokenEntity refTokenEntity);

    int deleteMember(UserMemberDto userMemberDto);
}
