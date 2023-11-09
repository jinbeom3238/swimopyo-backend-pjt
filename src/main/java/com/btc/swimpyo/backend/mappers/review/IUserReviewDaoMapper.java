package com.btc.swimpyo.backend.mappers.review;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserReviewDaoMapper {
    /*
     * 등록
     */
    // review 정보 db 저장(이미지 제외)
    public int insertReview(UserReviewDto userReviewDto);
    // db에 저장된 review 번호 가져오기
    public int selectReviewNo(UserReviewDto userReviewDto);
    // review 이미지 db에 저장
    public int insertReviewImg(UserReviewDto userReviewDto);
    // 주소 값 db에 저장
    public int insertReviewAddress(UserReviewDto userReviewInfoDto);

}
