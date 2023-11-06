package com.btc.swimpyo.backend.controller.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Log4j2
@RestController("/api/kakaoMap/")
public class KakaoMapApiController {

    public String getKakaoApiFromAddress(String roadFullAddr) {
        String apiKey = "b8ea41a8396eea63de6c55a4a488e132";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null;

        try {
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");

            String addr = apiUrl + "?query=" + roadFullAddr;

            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();

            String line;

            while ((line=rd.readLine()) != null) {
                docJson.append(line);
            }

            jsonString = docJson.toString();
            rd.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @GetMapping("/get_address_from_coords")
    public ResponseEntity<String> getAddressFromCoords(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude) {
        String address = "";
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + longitude + "&y=" + latitude;
            address = coordToAddr(apiUrl);
            log.info("apiUrl: " + apiUrl);
            return ResponseEntity.ok("주소: " + address);
        } catch (Exception e) {
            log.info("주소 API 요청 에러");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주소 조회에 실패했습니다.");
        }
    }

    @GetMapping("/get_address_from_api")
    public String getAddressFromApi(@RequestParam("apiUrl") String apiUrl) {
        String address = "";
        try {
            address = coordToAddr(apiUrl);
        } catch (Exception e) {
            System.out.println("주소 API 요청 에러");
            e.printStackTrace();
        }
        return address;
    }

    private static String coordToAddr(String apiUrl) throws Exception {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        // 인증키 - KakaoAK하고 한 칸 띄워주셔야해요!
        String auth = "KakaoAK " + "b8ea41a8396eea63de6c55a4a488e132";

        // URL 설정
        URL url = new URL(apiUrl);

        conn = (HttpURLConnection) url.openConnection();

        // Request 형식 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Requested-With", "curl");
        conn.setRequestProperty("Authorization", auth);

        // request에 JSON data 준비
        conn.setDoOutput(true);

        // 보내고 결과값 받기
        int responseCode = conn.getResponseCode();
        if (responseCode == 400) {
            System.out.println("400:: 해당 명령을 실행할 수 없음");
        } else if (responseCode == 401) {
            System.out.println("401:: Authorization가 잘못됨");
        } else if (responseCode == 500) {
            System.out.println("500:: 서버 에러, 문의 필요");
        } else { // 성공 후 응답 JSON 데이터 받기

            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return getRegionAddress(response.toString());
    }

    private static String getRegionAddress(String jsonString) {
        String value = "";
        JSONObject jObj = (JSONObject) JSONValue.parse(jsonString);
        JSONObject meta = (JSONObject) jObj.get("meta");
        long size = (long) meta.get("total_count");

        if (size > 0) {
            JSONArray jArray = (JSONArray) jObj.get("documents");
            JSONObject subJobj = (JSONObject) jArray.get(0);
            JSONObject roadAddress = (JSONObject) subJobj.get("road_address");

            if (roadAddress == null) {
                JSONObject subsubJobj = (JSONObject) subJobj.get("address");
                value = (String) subsubJobj.get("address_name");
            } else {
                value = (String) roadAddress.get("address_name");
            }

            if (value.equals("") || value == null) {
                subJobj = (JSONObject) jArray.get(1);
                subJobj = (JSONObject) subJobj.get("address");
                value = (String) subJobj.get("address_name");
            }
        }
        return value;
    }

}
