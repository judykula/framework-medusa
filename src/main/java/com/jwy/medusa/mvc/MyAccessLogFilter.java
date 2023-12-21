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
package com.jwy.medusa.mvc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *     在log中打印所有请求
 * </p>
 * <p>
 *      需要设置log level为debug级别，
 *      比如这样：{@code  logging.level.com.jwy.medusa.mvc.MyAccessLogFilter=debug}
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/24
 */
@Slf4j
@WebFilter(urlPatterns = {"/*"}, filterName = "AccessLogFilter")
public class MyAccessLogFilter implements Filter {

    /**这些header不需要log*/
    private Set<String> isolationLogHeaders = Sets.newHashSet();

    public MyAccessLogFilter() {
        isolationLogHeaders.add("sec-fetch-mode");
        isolationLogHeaders.add("sec-fetch-site");
        isolationLogHeaders.add("accept-language");
        isolationLogHeaders.add("sec-ch-ua");
        isolationLogHeaders.add("upgrade-insecure-requests");
        isolationLogHeaders.add("user-agent");
        isolationLogHeaders.add("sec-fetch-dest");
        isolationLogHeaders.add("accept");
        isolationLogHeaders.add("sec-fetch-user");
        isolationLogHeaders.add("accept-encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String url = request.getRequestURI();
        String method = request.getMethod();
        String headers = this.getHeaders(request);
        String params = request.getParameterMap().toString();

        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();

        if(log.isDebugEnabled() && !url.startsWith("/actuator")){
            log.debug("【REQ200】【{}】【{}】【{}】【{}】【{}】", url, end-start, method, headers, params);
        }
    }

    /**
     * get header s
     * @param request
     * @return Empty if none
     */
    private String getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = Maps.newHashMap();

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) return StringUtils.EMPTY;

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if(isolationLogHeaders.contains(headerName)) continue;
            headerMap.put(headerName, request.getHeader(headerName));
        }

        return headerMap.toString();
    }
}
