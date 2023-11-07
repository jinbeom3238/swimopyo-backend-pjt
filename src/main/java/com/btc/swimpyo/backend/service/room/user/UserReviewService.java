package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.mappers.reservation.user.IUserReservationDaoMapper;
import com.btc.swimpyo.backend.mappers.room.user.IUserReviewDaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserReviewService implements IUserReviewService{

    private final IUserReviewDaoMapper iUserReviewDaoMapper;

    @Override
    public int registReview(Map<String, Object> msgMap, UserReviewDto userReviewDto) {
        log.info("registReview");
//        userReviewDto.setA_r_no(Integer.parseInt(msgMap.get("a_r_no").toString()));
//        userReviewDto.setU_m_no(Integer.parseInt(msgMap.get("u_m_no").toString()));
//        userReviewDto.setP_am_no(Integer.parseInt(msgMap.get("p_am_no").toString()));
        userReviewDto.setR_title(msgMap.get("r_title").toString());
        userReviewDto.setR_content(msgMap.get("r_content").toString());
        userReviewDto.setR_image(msgMap.get("r_image").toString());
        userReviewDto.setR_sa_point(Integer.parseInt(msgMap.get("r_sa_point").toString()));

        int result;
        result = iUserReviewDaoMapper.insertReview(userReviewDto);

        return result;
    }



}
