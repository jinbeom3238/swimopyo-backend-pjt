package com.btc.swimpyo.backend.controller.api;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController("/api/GeoCoder/")
public class GeoCoderController {

    public static Map<String, Object> geoCoding(String address) {

        log.info("[geo]address: " + address);

        if (address == null)

            return null;

        Geocoder geocoder = new Geocoder();

        // setAddress : 변환하려는 주소 (경기도 성남시 분당구 등)

        // setLanguate : 인코딩 설정

        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("ko").getGeocoderRequest();

        GeocodeResponse geocoderResponse;


        try {

            geocoderResponse = geocoder.geocode(geocoderRequest);
            log.info("geores: " + geocoderResponse);

            if (geocoderResponse.getStatus() == GeocoderStatus.OK && !geocoderResponse.getResults().isEmpty()) {

                log.info("[geo~~~~~~]address:" + address);

                GeocoderResult geocoderResult=geocoderResponse.getResults().iterator().next();

                LatLng latitudeLongitude = geocoderResult.getGeometry().getLocation();

                Map<String, Object> msgData = new HashMap<>();
                msgData.put("lng",latitudeLongitude.getLng().floatValue()); // 경도
                msgData.put("lat",latitudeLongitude.getLat().floatValue()); // 위도
                msgData.put("address", address);

//                Float[] coords = new Float[2];

//                coords[0] = latitudeLongitude.getLat().floatValue();    //위도
//                coords[1] = latitudeLongitude.getLng().floatValue();    //경도

                return msgData;

            }

        } catch (IOException ex) {

            ex.printStackTrace();

        }

        return null;

    }

}
