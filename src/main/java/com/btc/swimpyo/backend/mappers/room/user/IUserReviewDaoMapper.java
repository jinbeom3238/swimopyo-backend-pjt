package com.btc.swimpyo.backend.mappers.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserReviewDaoMapper {
    int insertReview(UserReviewDto userReviewDto);
}
