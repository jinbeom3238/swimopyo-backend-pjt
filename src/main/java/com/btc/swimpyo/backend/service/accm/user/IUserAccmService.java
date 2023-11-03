package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;

import java.util.List;
import java.util.Map;

public interface IUserAccmService {

    // 리스트 조회
    public Map<String, Object> showAccmList(AdminAccmDto adminAccmDto);

    // 상세페이지 조회
    public Map<String, Object> showAccmDetail(int a_acc_no);
}
