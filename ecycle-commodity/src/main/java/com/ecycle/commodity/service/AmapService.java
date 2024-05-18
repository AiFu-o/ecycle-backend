package com.ecycle.commodity.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wangweichen
 */
public interface AmapService {

    Object geocode(String latitude, String longitude);
}
