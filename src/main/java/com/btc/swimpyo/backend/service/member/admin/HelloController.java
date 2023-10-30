package com.btc.swimpyo.backend.service.member.admin;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api")
public class HelloController {

    @PostMapping("/hello")
    public ResponseEntity<String> hello(@RequestBody Map<String, Object> msgMap) {
        log.info("hello()");
        log.info("tp : {}", msgMap);
        return ResponseEntity.ok("hello");

    }

}
