package com.baeldung.oauth2request;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.util.MultiValueMap;

/**
 * 6. Token Request Extra Parameters
 *  인증후에 인증 요청 전달 처리함
 *  Our Converter transforms OAuth2AuthorizationCodeGrantRequest to a RequestEntity.
 *
 */
public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;
    
    public CustomRequestEntityConverter() {
        // 기본 변환기로 기분 기능을 제공하고 추가 파라미터는 본문에서 추가 하여 전송한다.
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }
    
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
        RequestEntity<?> entity = defaultConverter.convert(req);
        //6. Token Request Extra Parameters
        MultiValueMap<String, String> params = (MultiValueMap<String,String>) entity.getBody();
        params.add("test2", "extra2");
        System.out.println(params.entrySet());
        return new RequestEntity<>(params, entity.getHeaders(), entity.getMethod(), entity.getUrl());
    }

}
