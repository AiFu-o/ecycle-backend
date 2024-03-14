package com.ecycle.commodity.web;

import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.service.feign.FeignTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 商品接口
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;


    @Autowired
    private FeignTestService feignTestService;


    @GetMapping("/queryAll")
    public Object queryAll(){
//        ResponseEntity<String> response = restTemplate.getForEntity("http://ecycle-auth/user/queryAll", String.class);
//        return response.getBody();
        return feignTestService.queryAll();
    }
}
