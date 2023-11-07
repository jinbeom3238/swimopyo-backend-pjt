package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;

import java.util.Map;

public interface IUserReviewService {
    int registReview(Map<String, Object> msgMap, UserReviewDto userReviewDto);
}
