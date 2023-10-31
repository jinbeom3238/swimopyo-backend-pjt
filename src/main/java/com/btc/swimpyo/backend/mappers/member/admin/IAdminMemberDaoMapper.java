package com.btc.swimpyo.backend.mappers.member.admin;

import com.btc.swimpyo.backend.dto.member.admin.AdminMemberDto;
import com.btc.swimpyo.backend.utils.jwt.entity.RefTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminMemberDaoMapper {

    int insertMember(AdminMemberDto adminMemberDto);

    AdminMemberDto isMember(AdminMemberDto adminMemberDto);

    int insertRefToken(RefTokenEntity refTokenEntity);

    RefTokenEntity selectRefToken(RefTokenEntity refTokenEntity);

    int deleteDupRefToken(RefTokenEntity checkedRefToken);

    int deleteMember(String userEmail);

    int updateAdmin(AdminMemberDto adminMemberDto);

    int updateAdminForPw(AdminMemberDto adminMemberDto);
}
