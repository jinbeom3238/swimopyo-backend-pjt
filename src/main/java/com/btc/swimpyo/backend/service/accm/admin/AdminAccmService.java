package com.btc.swimpyo.backend.service.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.mappers.accm.admin.IAdminAccmDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

        // 예외처리 - a_m_no 당 1개의 숙박시설만 등록할 수 있으므로 unique. 그러나 중복된 값을 넣는 경우 오류가 발생하므로 예외처리해주었음
        try {
            iAdminAccmDaoMapper.insertAccmInfo(adminAccmDto);
            log.info("a_m_no INSERT SUCCESS!!");
            
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.info("a_m_no ALREADY EXISTS!!");
                
            } else {
                e.printStackTrace();
                log.info("a_m_no INSERT FAIL!!");
                
            }
        }


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
