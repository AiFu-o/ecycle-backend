package com.ecycle.auth.handler;

import com.alibaba.fastjson.JSONObject;
import com.ecycle.auth.filter.WxPrincipal;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.UserService;
import com.ecycle.common.constants.SessionKeyConstant;
import com.ecycle.common.context.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description 微信登录成功
 */
@Component
@Log4j2
public class WxAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("微信登录成功");
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute(SessionKeyConstant.USERNAME, userInfo.getTelephone());
        session.setAttribute(SessionKeyConstant.USER_ID, userInfo.getUserId());
        session.setAttribute(SessionKeyConstant.TELEPHONE, userInfo.getTelephone());
        session.setAttribute(SessionKeyConstant.OPEN_ID, userInfo.getOpenId());
        JSONObject result = new JSONObject();
        result.put("token", session.getId());
        result.put(SessionKeyConstant.USER_ID, userInfo.getUserId());
        result.put(SessionKeyConstant.TELEPHONE, userInfo.getTelephone());
        result.put(SessionKeyConstant.OPEN_ID, userInfo.getOpenId());
        response.getWriter().append(result.toJSONString());
    }
}
