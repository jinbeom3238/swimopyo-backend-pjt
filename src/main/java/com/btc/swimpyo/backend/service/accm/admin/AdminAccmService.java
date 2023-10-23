package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.admin.IAdminAccmDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class AdminAccmService implements IAdminAccmService {

    private IAdminAccmDaoMapper iAdminAccmDaoMapper;

    public AdminAccmService(IAdminAccmDaoMapper iAdminAccmDaoMapper){
        this.iAdminAccmDaoMapper = iAdminAccmDaoMapper;
    }

    // 등록
    @Override
    public void registConfirm(String a_acc_image, AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmService] registConfirm()");

        /*adminAccmDto.setA_m_name((String) data.get("a_m_name"));
        adminAccmDto.setA_m_email((String) data.get("a_m_email"));*/

        adminAccmDto.setA_acc_image(a_acc_image);

        Map<String, Object> msgData = new HashMap<>();
        msgData.put("registInfo", adminAccmDto);
        log.info("msgData : " + msgData);

        iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);

    }

    // 상세페이지 보기
    @Override
    public AdminAccmDto showAccmDetail(int a_m_no) {
        log.info("[AdminAccmService] showAccmDetail()");

        AdminAccmDto adminAccmDtos = iAdminAccmDaoMapper.selectAccmInfo(a_m_no);

        return adminAccmDtos;

    }

    // 수정
    @Override
    public int modifyConfirm(AdminAccmDto adminAccmDto) {
        log.info("[AdminAccmService] modifyConfirm()");

        int result = iAdminAccmDaoMapper.updataeAccmInfo(adminAccmDto);

        if(result>0){
            log.info("[AdminAccmService] MODIFY ACCM SUCCESS!!");

            log.info("[AdminAccmService]: " + adminAccmDto);

            return result;

        } else {
            log.info("[AdminAccmService] MODIFY ACCM FAIL!!");

            return 0;

        }

    }

    // 삭제
    @Override
    public int deleteAccm(int a_m_no) {
        log.info("[AdminAccmService] modifyConfirm()");

        int result = iAdminAccmDaoMapper.deleteAccmInfo(a_m_no);

        if(result > 0) {
            log.info("[AdminAccmService] DELETE ACCM SUCCESS!!");

            return result;

        } else {
            log.info("[AdminAccmService] DELETE ACCM FAIL!!");

            return 0;

        }

    }
}
