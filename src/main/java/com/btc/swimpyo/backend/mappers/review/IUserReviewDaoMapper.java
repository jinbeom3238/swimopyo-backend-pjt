package com.btc.swimpyo.backend.mappers.review;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    public int insertReviewAddress(UserReviewDto userReviewDto);
    // isWrite = 1로 update
    public int updateIsWrite(int r_no);


    /*
     * [숙박시설 상세페이지] 리스트 조회
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
     * [룸 상세페이지] 리스트 조회
     */
    // 리뷰 정보 가져오기
    public List<UserReviewDto> selectReviewInfoInRoom(Map<String, Object> msgData);
    // r_no 가져오기
    public List<Integer> selectReviewRnoInRoom(Map<String, Object> msgData);

    /*
     * [마이페이지]상세페이지 조회
     */
    // 룸 정보 조회
    public UserReviewDto selectReviewDetail(UserReviewDto userReviewDto);
    // 이미지 정보 조회
    public List<UserReviewDto> selectReviewImgForDetail(int r_no);
    // 경도, 위도 정보 조회
    public List<UserReviewDto> selectReviewXYForDetail(int r_no);
    /*
     * [숙박업소]상세페이지 조회
     */
    // 룸 정보 조회
    public UserReviewDto selectReviewDetailForAccm(UserReviewDto userReviewDto);
    // a_acc_no 뽑아오기
    public int selectReviewAccNo(int r_no);



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

    // a_r_no
    public void selectArNo(String u_m_email);

    List<Map<String, Object>> selectReviewInfoAll(int aAccNo);

    void insertReviewLoc(UserReviewDto userReviewDto);
}
