package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IUserReviewService {

    // 등록
    public String registConfirm(UserReviewDto userReviewDto, List<UserReviewDto> address, MultipartFile[] reviewImages);

    // [숙박시설 상세페이지] 리스트 조회
    public Map<String, Object> showReviewList(int a_acc_no);
    // [룸 상세페이지] 리스트 조회
    public Map<String, Object> showReviewListRoom(int a_r_no, int a_acc_no);

    // [마이페이지] 상세페이지 조회
    public Map<String, Object> showDetailMyPage(int r_no, String u_m_email, UserReviewDto userReviewDto);

    // [숙박업소] 상세페이지 조회
    public Map<String, Object> showDetail(int r_no) throws JsonProcessingException;

    // 삭제
    public int deleteConfirm(UserReviewDto userReviewDto);


}
