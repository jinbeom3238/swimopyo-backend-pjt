package com.btc.swimpyo.backend.config;

import com.btc.swimpyo.backend.dto.accm.admin.AdminAccmDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AdminAccmDtoArgumentResolver implements HandlerMethodArgumentResolver {


    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AdminAccmDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 여기서 요청에서 AdminAccmDto를 추출하고 반환합니다.
        // 예를 들어, webRequest의 getParameter 메서드를 사용하여 필요한 값을 가져올 수 있습니다.
        // 이후 Jackson 라이브러리 등을 사용하여 JSON 문자열을 AdminAccmDto 객체로 변환할 수 있습니다.
        // AdminAccmDto의 각 필드를 가져옵니다.
        String a_acc_name = webRequest.getParameter("a_acc_name");
        String a_acc_intro = webRequest.getParameter("a_acc_intro");
        String a_acc_kind = webRequest.getParameter("a_acc_kind");
        String a_acc_bn = webRequest.getParameter("a_acc_bn");
        String a_m_no = webRequest.getParameter("a_m_no");
        String a_m_email = webRequest.getParameter("a_m_email");
        String a_acc_address = webRequest.getParameter("a_acc_address");
        String a_acc_phone = webRequest.getParameter("a_acc_phone");

        // JSON 문자열로 변환합니다.
        String json = String.format(
                "{\"a_acc_name\": \"%s\", \"a_acc_intro\": \"%s\", \"a_acc_kind\": \"%s\", \"a_acc_bn\": \"%s\", \"a_m_no\": \"%s\", \"a_m_email\": \"%s\", \"a_acc_address\": \"%s\", \"a_acc_phone\": \"%s\"}",
                a_acc_name, a_acc_intro, a_acc_kind, a_acc_bn, a_m_no, a_m_email,a_acc_address, a_acc_phone
        );

        // JSON 문자열을 AdminAccmDto 객체로 변환합니다.
        AdminAccmDto adminAccmDto = objectMapper.readValue(json, AdminAccmDto.class);

        return adminAccmDto;
    }
}
