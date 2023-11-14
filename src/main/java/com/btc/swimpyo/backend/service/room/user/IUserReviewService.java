package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IUserReviewService {

    // 등록
    public String registConfirm(UserReviewDto userReviewDto, List<String> r_xy_address, MultipartFile[] reviewImages);

    // 리스트 조회
    // public Map<String, Object> showReviewList(String u_m_email);
    public Map<String, Object> showReviewList(int a_acc_no);

    // 상세페이지 조회
    public Map<String, Object> showDetail(int r_no, String u_m_email, UserReviewDto userReviewDto);

    // 삭제
    public int deleteConfirm(UserReviewDto userReviewDto);


}
