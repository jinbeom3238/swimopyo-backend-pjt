package com.btc.swimpyo.backend.service.room.user;

import com.btc.swimpyo.backend.dto.room.user.UserReviewDto;
import com.btc.swimpyo.backend.mappers.review.IUserReviewDaoMapper;
import com.btc.swimpyo.backend.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserReviewService implements IUserReviewService{

    private final IUserReviewDaoMapper iUserReviewDaoMapper;
    private final S3Uploader s3Uploader;

//    @Override
//    public int registReview(Map<String, Object> msgMap, UserReviewDto userReviewDto) {
//        log.info("registReview");
////        userReviewDto.setA_r_no(Integer.parseInt(msgMap.get("a_r_no").toString()));
////        userReviewDto.setU_m_no(Integer.parseInt(msgMap.get("u_m_no").toString()));
////        userReviewDto.setP_am_no(Integer.parseInt(msgMap.get("p_am_no").toString()));
//        userReviewDto.setR_title(msgMap.get("r_title").toString());
//        userReviewDto.setR_content(msgMap.get("r_content").toString());
//        userReviewDto.setR_image(msgMap.get("r_image").toString());
//        userReviewDto.setR_sa_point(Integer.parseInt(msgMap.get("r_sa_point").toString()));
//
//        int result;
//        result = iUserReviewDaoMapper.insertReview(userReviewDto);
//
//        return result;
//    }

    /*
     * 리뷰 등록
     * 1. 예약한 내역이 있는 사용자만 예약할 수 있다. -> 예약 번호 일치해야한다.
     * 2. 예약한 사용자의 email과 r_no가 일치해야만 등록할 수 있다.
     */

    // 등록
    @Override
    public String registReview(UserReviewDto userReviewDto, MultipartFile[] reviewImages) {
        log.info("[UserReviewService] registReview()");

        log.info("[registReview] userReviewDto : " + userReviewDto);

            log.info("try");
            // 1. tbl_review 테이블에 데이터 등록
            int result = iUserReviewDaoMapper.insertReview(userReviewDto);
            log.info("result: " + result);
//            log.info("u_m_email:" + userReviewInfoDto.getU_m_email());

            // 2. 등록된 리뷰 테이블 번호 가져오기
            int r_no = iUserReviewDaoMapper.selectReviewNo(userReviewDto);
            log.info("r_no: " + r_no);
            userReviewDto.setR_no(r_no);
            log.info("r_no: " + userReviewDto.getR_no());

//            userReviewInfoDto.setU_r_no(r_no);

            // 3. front에서 입력받은 주소 값 db에 저장
            int isReviewAddress = iUserReviewDaoMapper.insertReviewAddress(userReviewDto);
            log.info("isReviewAddress:" + isReviewAddress);

            // 4. tbl_review_image 테이블에 이미지 정보 등록
            for (MultipartFile file : reviewImages) {
                log.info("reviewImages: " + reviewImages);

                String imageUrl = s3Uploader.uploadFileToS3(file, "static/test");
                userReviewDto.setR_ri_image(imageUrl);

                log.info("imageUrl: " + userReviewDto.getR_ri_image());

                result = iUserReviewDaoMapper.insertReviewImg(userReviewDto);
                log.info("result:" + result);
                
                if (result > 0) {
                    log.info("등록 성공");

                } else {
                     log.info("등록 실패");

                }

            }
            // 4. 이미지 업로드가 완료되면 이미지 url을 반환
            return "이미지 업로드가 완료되었습니다.";

    }

    // 리스트 조회
    @Override
    public Map<String, Object> showReviewList(String u_m_email) {
        log.info("[userReviewService] showReviewList()");

        Map<String, Object> msgData = new HashMap<>();
        List<UserReviewDto> r_ri_imagesAndXY = new ArrayList<>();
        List<UserReviewDto> r_xy_address = new ArrayList<>();
        int r_no;

        // 리뷰 리스트 가져오기(이미지 제외)
        List<UserReviewDto> userReviewDto = iUserReviewDaoMapper.selectReviewInfo(u_m_email);
        log.info("userReviewDtos: " + userReviewDto);

        // 사용자가 작성한 리뷰 번호 가져오기
        List<Integer> r_nos = userReviewDto.stream()
                .map(UserReviewDto::getR_no)
                .collect(Collectors.toList());
        log.info("r_nos: " + r_nos.get(0));

        // r_no에 대한 이미지, 주소 정보 들고 오기
        for(int i = 0; i<r_nos.size(); i++) {
            log.info("r_nos: " + r_nos);

            r_no = r_nos.get(i);
            log.info("r_no: " + r_no);

            // 주소 정보 가져오기
//            r_xy_address = iUserReviewDaoMapper.selectReviewAddress(r_no);

            // u_ri_no 가져오기(이미지 번호)
            List<Integer> u_ri_nos = iUserReviewDaoMapper.selectReviewImgNo(r_no);
            log.info("u_ri_nos: " + u_ri_nos);

            // 이미지 정보 가져오기
            r_ri_imagesAndXY = iUserReviewDaoMapper.selectReviewImgForList(r_no);
            log.info("image and xy: " + r_ri_imagesAndXY);

        }

        msgData.put("u_ri_images", r_ri_imagesAndXY);
        msgData.put("userReviewDto", userReviewDto);

        log.info(msgData);

        return msgData;

    }
}
