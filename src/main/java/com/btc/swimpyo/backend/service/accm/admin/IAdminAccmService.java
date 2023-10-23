package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;

public interface IAdminAccmService {

    // 등록
    public void registConfirm(AdminAccmDto adminAccmDto);

    //상세페이지 보기
    public AdminAccmDto showAccmDetail(int a_acc_no);

    // 수정
    public int modifyConfirm(AdminAccmDto adminAccmDto);

    // 삭제
    public int deleteAccm(int a_m_no);
}
