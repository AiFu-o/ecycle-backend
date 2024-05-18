package com.ecycle.commodity.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecycle.commodity.model.UserAddress;
import com.ecycle.commodity.service.AmapService;
import com.ecycle.common.context.RestResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/5/17
 * @Description 获取区域数据接口
 */
@RestController
@Log4j2
@RequestMapping("/region-data")
public class RegionDataController {
    private JSONObject streetList = null;

    private JSONObject areaList = null;

    private JSONObject cityList = null;

    private JSONArray provinceList = null;

    @Resource
    private AmapService amapService;

    @GetMapping("/geocode")
    public RestResponse<Object> geocode(@RequestParam(name = "latitude") String latitude,
                                              @RequestParam(name = "longitude") String longitude) {

        return RestResponse.success(amapService.geocode(latitude, longitude));
    }

    @GetMapping("/getStreetList/{areaCode}")
    public RestResponse<Object> getStreetList(@PathVariable(name = "areaCode") String areaCode) {
        if (null == streetList) {
            try {
                streetList =  JSONObject.parseObject(getJsonStingByFileName("street-list.json"));
            } catch (IOException e) {
                log.error("获取数据文件失败", e);
                return RestResponse.validfail("获取数据文件失败");
            }
        }
        return RestResponse.success(streetList.getJSONArray(areaCode));
    }

    @GetMapping("/getAreaList/{cityCode}")
    public RestResponse<Object> getAreaList(@PathVariable(name = "cityCode") String cityCode) {
        if (null == areaList) {
            try {
                areaList =  JSONObject.parseObject(getJsonStingByFileName("area-list.json"));
            } catch (IOException e) {
                log.error("获取数据文件失败", e);
                return RestResponse.validfail("获取数据文件失败");
            }
        }
        return RestResponse.success(areaList.getJSONArray(cityCode));
    }

    @GetMapping("/getCityList/{provinceCode}")
    public RestResponse<Object> getCityList(@PathVariable(name = "provinceCode") String provinceCode) {
        if (null == cityList) {
            try {
                cityList =  JSONObject.parseObject(getJsonStingByFileName("city-list.json"));
            } catch (IOException e) {
                log.error("获取数据文件失败", e);
                return RestResponse.validfail("获取数据文件失败");
            }
        }
        return RestResponse.success(cityList.getJSONArray(provinceCode));
    }

    @GetMapping("/getProvinceList")
    public RestResponse<Object> getProvinceList() {
        if (null == provinceList) {
            try {
                provinceList =  JSONArray.parseArray(getJsonStingByFileName("province-list.json"));
            } catch (IOException e) {
                log.error("获取数据文件失败", e);
                return RestResponse.validfail("获取数据文件失败");
            }
        }
        return RestResponse.success(provinceList);
    }

    private String getJsonStingByFileName(String fileName) throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource("address/" + fileName);
        return new String(resource.getInputStream().readAllBytes());
    }

}
