package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUserReviewService {

    // 등록
    public String registReview(UserReviewDto userReviewDto, MultipartFile[] reviewImages);
}
