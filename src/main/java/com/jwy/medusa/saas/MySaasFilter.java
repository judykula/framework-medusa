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
package com.jwy.medusa.saas;

import com.jwy.medusa.utils.MyHttpHeaders;
import com.jwy.medusa.utils.spring.MyContextUtils;
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
 *      将需要透传的"SaaS头信息"增加到HTTP请求上下文中
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/20
 */
@Slf4j
@WebFilter(urlPatterns = {"/*"}, filterName = "MySaasFilter")
public class MySaasFilter implements Filter {

    @Autowired
    private MyContextUtils myContextUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/actuator")) return;

        String tenantJson = request.getHeader(MyHttpHeaders.REQUEST_SaaS_TENANT);
        try {
            Tenant tenant = myContextUtils.jsonUtils().toObj(tenantJson, Tenant.class);
            this.myContextUtils.setTenant(tenant);
            log.debug("【MSF056】context with tenant: {}", tenant);
        } catch (Exception e) {
            log.error("【MSF058】tenant serialize fail", e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
        this.myContextUtils.clearAllContext();
    }
}
