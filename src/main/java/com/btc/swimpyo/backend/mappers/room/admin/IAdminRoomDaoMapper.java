package com.btc.swimpyo.backend.mappers.room.admin;

import com.btc.swimpyo.backend.dto.room.admin.AdminRoomDto;
import com.btc.swimpyo.backend.dto.room.admin.AdminRoomImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminRoomDaoMapper {

    // 등록
    public int insertRoomInfo(AdminRoomDto adminRoomDto);   // 이미지를 제외한 룸 정보 등록

    public List<Integer> selectRoomForArNo(AdminRoomDto adminRoomDto);   // a_r_no를 가져오기 위함

    public int insertRoomImage(AdminRoomImageDto adminRoomImageDto);   // Room 이미지 등록

    /*
     * 상세페이지 조회
     */
    // Room 정보 조회(이미지 제외)
    public AdminRoomDto selectRoomInfo(int a_m_no);
    // Room 번호 front에 보내기
    public List<Integer> selectRoomImgNo(int a_r_no);
    // Room 이미지 조회
    public List<String> selectRoomImg(int a_r_no);

    /*
     * 수정
     */
    // 이미지를 제외한 숙박시설 정보 update
    public int updateRoomInfo(AdminRoomDto adminRoomDto);
    // front에서 요청받은 r_i_no 리스트들에 대한 image 값들을 들고 오기 위함
    public List<String> selectRoomImgs(int deleteNo);
    // deleteNo를 통해 기존 이미지 삭제
    public int deleteRoomImgs(int deleteNo);

    /*
     * 삭제
     */
    // 이미지를 제외한 Room 정보 삭제
    public void deleteRoomInfo(int a_acc_no);
    // 이미지 삭제
    public int deleteRoomImg(int a_r_no);

    /*
    /*
     * Room 리스트 조회
     */
    // Room 정보 조회(이미지 제외)
    public List<AdminRoomDto> selectRoomInfoForList(int a_acc_no);
    // Room 이미지 조회
    public List<AdminRoomImageDto> selectRoomImgForList(int a_r_no);
}
