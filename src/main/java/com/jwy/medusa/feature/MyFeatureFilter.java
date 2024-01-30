/*
 * easy come, easy go.
 *
 * contact : syiae.jwy@gmail.com
 *
 * · · · · ||   ..     __       ___      ____  ®
 * · · · · ||  ||  || _ ||   ||    ||   ||      ||
 * · · · · ||  ||  \\_ ||_.||    ||   \\_  ||
 * · · _//                                       ||
 * · · · · · · · · · · · · · · · · · ·· ·    ___//
 */
package com.jwy.medusa.feature;

import com.jwy.medusa.common.utils.MyHttpHeaders;
import com.jwy.medusa.common.utils.spring.MyContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *     将需要透传的"灰度路由信息"增加到HTTP请求上下文中
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Slf4j
@WebFilter(urlPatterns = {"/*"}, filterName = "MyFeatureFilter")
public class MyFeatureFilter implements Filter {

    @Autowired
    private MyContextUtils myContextUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/actuator")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String feature = request.getHeader(MyHttpHeaders.REQUEST_FEATURE);
        this.myContextUtils.setFeature(feature);
        log.debug("【MFF054】context with feature: {}", feature);

        filterChain.doFilter(servletRequest, servletResponse);
        this.myContextUtils.clearFeatureContext();
    }
}
