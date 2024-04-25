package com.ecycle.auth.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description TODO
 */
public class CustomConcurrentSessionFilter extends GenericFilterBean {

    // ~ Instance fields
    // ================================================================================================

    private final SessionRegistry sessionRegistry;
    private String expiredUrl;
    private LogoutHandler handlers = new CompositeLogoutHandler(new SecurityContextLogoutHandler());
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    // ~ Methods
    // ========================================================================================================

    public CustomConcurrentSessionFilter(SessionRegistry sessionRegistry) {
        Assert.notNull(sessionRegistry, "SessionRegistry required");
        this.sessionRegistry = sessionRegistry;
        this.sessionInformationExpiredStrategy = new ResponseBodySessionInformationExpiredStrategy();
    }



    public CustomConcurrentSessionFilter(SessionRegistry sessionRegistry, SessionInformationExpiredStrategy sessionInformationExpiredStrategy) {
        Assert.notNull(sessionRegistry, "sessionRegistry required");
        Assert.notNull(sessionInformationExpiredStrategy, "sessionInformationExpiredStrategy cannot be null");
        this.sessionRegistry = sessionRegistry;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(sessionRegistry, "SessionRegistry required");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (session != null) {
            SessionInformation info = sessionRegistry.getSessionInformation(session
                    .getId());

            if (info != null) {
                if (info.isExpired()) {
                    // Expired - abort processing
                    if (logger.isDebugEnabled()) {
                        logger.debug("Requested session ID "
                                + request.getRequestedSessionId() + " has expired.");
                    }
                    doLogout(request, response);

                    this.sessionInformationExpiredStrategy.onExpiredSessionDetected(new SessionInformationExpiredEvent(info, request, response));
                    return;
                }
                else {
                    // Non-expired - update last request date/time
                    sessionRegistry.refreshLastRequest(info.getSessionId());
                }
            }
        }

        chain.doFilter(request, response);
    }

    protected String determineExpiredUrl(HttpServletRequest request,
                                         SessionInformation info) {
        return expiredUrl;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        this.handlers.logout(request, response, auth);
    }

    public void setLogoutHandlers(LogoutHandler[] handlers) {
        this.handlers = new CompositeLogoutHandler(handlers);
    }


    private static final class ResponseBodySessionInformationExpiredStrategy
            implements SessionInformationExpiredStrategy {
        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
                throws IOException, ServletException {
            HttpServletResponse response = event.getResponse();
            Object principal = event.getSessionInformation().getPrincipal();
            response.sendError(HttpServletResponse.SC_CONFLICT, principal + " session concurrent。");
        }
    }
}