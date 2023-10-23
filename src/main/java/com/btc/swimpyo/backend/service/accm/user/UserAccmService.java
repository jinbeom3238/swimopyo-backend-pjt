package com.btc.swimpyo.backend.service.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.user.IUserAccmDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserAccmService implements IUserAccmService{

    private IUserAccmDaoMapper iUserAccmDaoMapper;

    public UserAccmService(IUserAccmDaoMapper iUserAccmDaoMapper) {
        this.iUserAccmDaoMapper = iUserAccmDaoMapper;
    }

    // 리스트 조회
    @Override
    public List<AdminAccmDto> showAccmList(AdminAccmDto adminAccmDto) {
        log.info("[UserAccmService] showAccmList()");

        List<AdminAccmDto> adminAccmDtos = iUserAccmDaoMapper.selectAccmList(adminAccmDto);

        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDtos);

        return adminAccmDtos;

    }

    // 상세페이지 보기
    @Override
    public AdminAccmDto showAccmDetail(int a_acc_no) {
        log.info("[UserAccmService] showAccmDetail()");

        AdminAccmDto adminAccmDtos = iUserAccmDaoMapper.selectAccmDetail(a_acc_no);

        log.info("[UserAccmService] adminAccmDtos: " + adminAccmDtos);

        return adminAccmDtos;

    }


}
