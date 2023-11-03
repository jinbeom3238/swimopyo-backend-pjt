package com.btc.swimpyo.backend.mappers.accm.admin;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminAccmDaoMapper {

    /*
     * 등록
     */
    public int insertAccmInfo(AdminAccmDto adminAccmDto);
    public int selectAccmForAmNo(int a_m_no);
    public int insertAccmImage(AdminAccmImageDto adminAccmImageDto);

    /*
     * 상세페이지 조회
     */
    public AdminAccmDto selectAccmInfo(int a_m_no);
    public List<String> selectAccmImg(int a_acc_no);
    // 숙박시설 a_i_no front에 보내주기 위해 추가
    public List<Integer> selectAccmImgNo(int a_acc_no);
    // 주소 값(a_acc_address)을 경도, 위도 값으로 변경해줌
    public void insertAccmLoc(Map<String, Object> coords);

    /*
     * 수정
     */
    // 이미지를 제외한 숙박시설 정보 update
    public int updateAccmInfo(AdminAccmDto adminAccmDto);
    // front에서 요청받은 삭제할 a_i_no 리스트들에 대한 image 값들을 들고 오기 위함
    public List<String> selectAccmImgs(int deleteNo);
    // deleteNo를 통해 기존 이미지 삭제
    public int deleteAccmdelImgs(int deleteNo);

    /*
     * 삭제
     */
    public int deleteAccmInfo(int a_m_no);
    public int deleteAccmImg(int a_acc_no);  // 새로운 사진 추가 전 delete

}
