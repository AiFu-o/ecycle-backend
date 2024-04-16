package com.ecycle.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.utils.JwtTokenUtils;
import com.ecycle.gateway.config.properties.IgnoreWhiteProperties;
import com.ecycle.gateway.web.RestResponse;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/1
 * @Description TODO
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered, TokenConstants {

    @Autowired
    private IgnoreWhiteProperties ignoreWhite;
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();

        // 跳过不需要验证的路径
        if (matches(url, ignoreWhite.getUrls())) {
            return chain.filter(exchange);
        }
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtTokenUtils.parseToken(token);
        if (claims == null || claims.getExpiration().getTime() < System.currentTimeMillis()) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }

        String userid = claims.getSubject();
        String username = JwtTokenUtils.getValue(claims, TokenConstants.F_USERNAME);

        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(PREFIX)) {
            token = token.replaceFirst(PREFIX, StringUtils.EMPTY);
        }
        return token;
    }


    private Boolean matches(String str, List<String> urls) {
        if (StringUtils.isEmpty(str) || urls.isEmpty()) {
            return false;
        }
        for (String pattern : urls) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        RestResponse<String> result = RestResponse.validfail(msg);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
