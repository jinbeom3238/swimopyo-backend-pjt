package com.btc.swimpyo.backend.service.accm.admin;

import java.util.List;
import java.util.Map;

public interface IAdminSearchAccmService {


    List<Map<String, Object>> rezList(int a_m_no);

    int checkAccm(int a_m_no);
}
