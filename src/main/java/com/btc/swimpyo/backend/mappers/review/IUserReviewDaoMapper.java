package com.btc.swimpyo.backend.mappers.review;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /*
     * 리스트 조회
     */
    // 리뷰 정보 가져오기
    public List<UserReviewDto> selectReviewInfo(String u_m_email);
    // 이미지 번호(u_ri_no) 가져오기
    public List<Integer> selectReviewImgNo(int r_no);
    // 이미지 정보 가져오기
    public List<UserReviewDto> selectReviewImgForList(int r_no);

    /*
     * 상세페이지 조회
     */
    // 룸 정보 조회
    public UserReviewDto selectReviewDetail(UserReviewDto userReviewDto);
    // 이미지 정보 조회
    public List<UserReviewDto> selectReviewImgForDetail(int r_no);
    // 경도, 위도 정보 조회
    public List<UserReviewDto> selectReviewXYForDetail(int rNo);
}
