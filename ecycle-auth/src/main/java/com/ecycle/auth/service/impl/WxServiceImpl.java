package com.ecycle.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ecycle.auth.config.properties.WxMpProperties;
import com.ecycle.auth.service.WxService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description TODO
 */
@Service
@Log4j2
public class WxServiceImpl implements WxService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private WxMpProperties wxMpProperties;

    @Override
    public String getAccessToken() {
        String redisAccessTokenKey = "WX_MP_ACCESS_TOKEN";
        if (null != redisTemplate.opsForValue().get(redisAccessTokenKey)) {
            return (String) redisTemplate.opsForValue().get(redisAccessTokenKey);
        }
        String url = wxMpProperties.getUrl() +
                "/cgi-bin/token";

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("grant_type", "authorization_code");
        uriVariables.put("appid", wxMpProperties.getAppid());
        uriVariables.put("secret", wxMpProperties.getSecret());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("获取 access_token 失败 状态码:{}", response.getStatusCodeValue());
            return null;
        }
        JSONObject responseBody = JSONObject.parseObject(response.getBody());
        if (responseBody.containsKey("errcode")) {
            log.error("获取 access_token 失败 {}", responseBody.toJSONString());
            return null;
        }
        String accessToken = responseBody.getString("access_token");
        Long expiresIn = responseBody.getLong("expires_in");

        redisTemplate.opsForValue().set(redisAccessTokenKey, accessToken, expiresIn, TimeUnit.SECONDS);
        return accessToken;
    }

    @Override
    public Map<String, String> getMiniAppSessionId(String jsCode) {
        String accessToken = getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            throw new NullPointerException("accessToken is not empty!");
        }
        String url = wxMpProperties.getUrl() +
                "/wxa/getpluginopenpid?access_token=" + accessToken;

        JSONObject requestBody = new JSONObject();
        requestBody.put("code", jsCode);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toJSONString(), null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AuthenticationServiceException("jscode2session error status: " + response.getStatusCodeValue());
        }
        JSONObject responseBody = JSONObject.parseObject(response.getBody());
        if (responseBody.getIntValue("errcode") != 0) {
            throw new AuthenticationServiceException("jscode2session error body: " + responseBody.toJSONString());
        }
        String openId = responseBody.getString("openid");
        String sessionId = responseBody.getString("sessionId");
        Map<String, String> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("openId", openId);
        return result;
    }

    @Override
    public String getTelephone(String phoneCode, String openId) {
        String accessToken = getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            throw new NullPointerException("accessToken is not empty!");
        }
        String url = wxMpProperties.getUrl() +
                "/wxa/business/getuserphonenumber?access_token=" + accessToken;

        JSONObject requestBody = new JSONObject();
        requestBody.put("code", phoneCode);
        if (StringUtils.isNotEmpty(openId)) {
            requestBody.put("openId", phoneCode);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toJSONString(), null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AuthenticationServiceException("jscode2session error status: " + response.getStatusCodeValue());
        }
        JSONObject responseBody = JSONObject.parseObject(response.getBody());
        if (responseBody.getIntValue("errcode") != 0) {
            throw new AuthenticationServiceException("jscode2session error body: " + responseBody.toJSONString());
        }
        return responseBody.getJSONObject("phone_info").getString("purePhoneNumber");
    }

    @Override
    public Boolean checkSessionId(String openId, String sessionId) {
        try {
            String accessToken = getAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                throw new NullPointerException("accessToken is not empty!");
            }
            String url = wxMpProperties.getUrl() +
                    "/wxa/checksession";

            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("access_token", accessToken);
            uriVariables.put("openid", openId);
            String signature = getCheckSessionIdSign(sessionId);
            uriVariables.put("signature", signature);
            uriVariables.put("secret", "hmac_sha256");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new AuthenticationServiceException("jscode2session error status: " + response.getStatusCodeValue());
            }
            JSONObject responseBody = JSONObject.parseObject(response.getBody());
            if (responseBody.getIntValue("errcode") != 0) {
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getCheckSessionIdSign(String sessionId) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(sessionId.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKeySpec);

        byte[] dataBytes = "".getBytes(StandardCharsets.UTF_8);
        byte[] signatureBytes = hmacSha256.doFinal(dataBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : signatureBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
