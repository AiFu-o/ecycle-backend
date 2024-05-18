package com.ecycle.commodity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ecycle.commodity.service.AmapService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangweichen
 * @Date 2024/5/17
 * @Description TODO
 */
@Service
@Log4j2
public class AmapServiceImpl implements AmapService {

    @Value("${amap.key:}")
    private String amapKey;

    @Override
    public Object geocode(String latitude, String longitude) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", amapKey);
        params.put("location", longitude + "," + latitude);
        StringBuilder url = new StringBuilder();
        url.append("https://restapi.amap.com/v3/geocode/regeo");
        url.append("?location=").append(longitude).append(",").append(latitude);
        url.append("&key=").append(amapKey);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("逆地理编码失败:{}", response.getBody());
            return "";
        }
        JSONObject result = JSONObject.parseObject(response.getBody());
        if(!result.getString("status").equals("1")){
            log.error("逆地理编码失败:{}", response.getBody());
            return "";
        }
        return result;
    }
}
