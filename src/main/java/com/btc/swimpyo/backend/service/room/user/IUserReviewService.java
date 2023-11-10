package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUserReviewService {

    // 등록
    public String registConfirm(UserReviewDto userReviewDto, MultipartFile[] reviewImages);

    // 리스트 조회
    public Map<String, Object> showReviewList(String u_m_email);

    // 상세페이지 조회
    public Map<String, Object> showDetail(int r_no, String u_m_email, UserReviewDto userReviewDto);

    // 삭제
    public int deleteConfirm(UserReviewDto userReviewDto);
}
