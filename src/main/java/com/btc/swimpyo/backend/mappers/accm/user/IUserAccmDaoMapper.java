package com.btc.swimpyo.backend.mappers.accm.user;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserAccmDaoMapper {

    /*
     * 리스트 조회
     */
    // 이미지 제외한 정보
    public List<AdminAccmDto> selectAccmList(AdminAccmDto adminAccmDto);
    // a_acc_no 값 조회
    public List<Integer> selectAccmNo(AdminAccmDto adminAccmDto);
    // 이미지 조회
    public List<AdminAccmImageDto> selectAccmImgList(int a_acc_no);

    // 상세페이지 조회
    public AdminAccmDto selectAccmDetail(int a_acc_no);
    // 주소 값을 경도, 위도 값으로 바꿔주기 위함
    public void insertAccmLoc(AdminAccmDto adminAccmDto);
    


    public List<Integer> selectAccmImgNo(int a_acc_no);
    // 별점 가져오기
    public Double selectReviewStar(int aAccNo);
}
