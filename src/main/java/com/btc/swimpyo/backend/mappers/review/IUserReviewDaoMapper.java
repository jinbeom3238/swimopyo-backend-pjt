package com.btc.swimpyo.backend.mappers.review;

import com.btc.swimpyo.backend.dto.reservation.ReservationDto;
import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

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
    public int insertReviewAddress(List<UserReviewDto> r_xy_address);

    /*
     * [마이페이지] 리스트 조회
     */
    // 리뷰 정보 가져오기
    public List<UserReviewDto> selectReviewInfo(int a_acc_no);
    // 이미지 번호(u_ri_no) 가져오기
    public List<Integer> selectReviewImgNo(int r_no);
    // 이미지 정보 가져오기
    public List<UserReviewDto> selectReviewImgForList(int a_acc_no);
    // 주소 정보 가져오기
    public List<UserReviewDto> selectReviewAddressForList(int r_no);
    // r_no 가져오기
    public List<Integer> selectReviewRno(int a_acc_no);

    /*
     * 상세페이지 조회
     */
    // 룸 정보 조회
    public UserReviewDto selectReviewDetail(UserReviewDto userReviewDto);
    // 이미지 정보 조회
    public List<UserReviewDto> selectReviewImgForDetail(int r_no);
    // 경도, 위도 정보 조회
    public List<UserReviewDto> selectReviewXYForDetail(int rNo);

    /*
     * 삭제
     */
    // 이미지를 제외한 리뷰 정보 삭제
    public int deleteReview(UserReviewDto userReviewDto);
    // 삭제할 이미지 가져오기
    public List<String> selectReviewImg(UserReviewDto userReviewDto);
    // 이미지 db에서 삭제하기
    public int deleteReviewImg(UserReviewDto userReviewDto);
    // 삭제할 주소 가져오기
    public List<String> selectReviewAddress(UserReviewDto userReviewDto);
    // 주소 db에서 삭제하기
    public int deleteReviewAddress(UserReviewDto userReviewDto);

    
}
