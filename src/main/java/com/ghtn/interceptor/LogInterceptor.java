package com.ghtn.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * User: Administrator
 * Date: 13-11-8
 * Time: 下午5:17
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(LogInterceptor.class);

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.debug("modelAndView:" + modelAndView);
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("request:" + request.getRequestURI());
        return super.preHandle(request, response, handler);
    }
}
