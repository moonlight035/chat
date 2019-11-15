package cn.moonlight035.chatclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */

public class HttpRequestUtil {

    public static Object sendPostRequest(MultiValueMap<String, String> paramMap, String url){
        RestTemplate restTemplate = new RestTemplate();
        Object o = restTemplate.postForObject(url, paramMap, Object.class);
        return o;
    }
}
