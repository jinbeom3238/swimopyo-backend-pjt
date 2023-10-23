package com.btc.swimpyo.backend.service.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.mappers.room.admin.IAdminRoomDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminRoomService implements IAdminRoomService{

    private final IAdminRoomDaoMapper iAdminRoomDaoMapper;

    // 등록
    @Override
    public int registConfirm(int a_acc_no, AdminRoomDto adminRoomDto) {
        log.info("[AdminRoomService] registConfirm()");



        int result = iAdminRoomDaoMapper.insertRoomInfo(adminRoomDto);

        log.info("adminRoomDto: " + adminRoomDto);
        log.info("adminRoomDto: " + adminRoomDto.getA_acc_no());


        if(result > 0) {
            log.info("[AdminRoomService] ROOM REGIST SUCCESS!!");

            return result;

        } else {
            log.info("[AdminRoomService] ROOM REGIST FAIL!!");

            return 0;

        }

    }
}
