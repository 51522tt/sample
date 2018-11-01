package com.insunny.sample.service;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.apache.ibatis.reflection.ArrayUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
@Service
public class HttpClient {
    public String client(String url, HttpMethod method, MultiValueMap<String,String> parms){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(parms, headers);
        ResponseEntity<String> response= template.postForEntity(url,request,String.class);

        return response.getBody();
    }

    public List<String> bodyConverter(String source){
        String[] accountArr=source.substring(1, source.length() - 1).split(",");
        List<String> accounts = new ArrayList<>();
        System.out.println(accountArr.length);
        System.out.println(ArrayUtil.toString(accountArr)  );
        if(accountArr.length >1){
            for (String s : accountArr) {
                System.out.println(s);
                accounts.add(s.substring(1, s.length() - 1));
            }
        }

        return accounts;
    }


}
