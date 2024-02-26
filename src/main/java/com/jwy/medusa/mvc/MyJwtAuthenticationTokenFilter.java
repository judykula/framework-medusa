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

import com.jwy.medusa.common.exception.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     JWT 的 authorization 验证。
 *     启用此功能代表你必须在每个请求中添加header： "authorization"，设置val为"Bearer ${token}", token为约定的值(可以选择加密)
 *
 *     在服务端你可以通过{@code my.jwt.token}来设置token值。
 *     通过{@code my.authorization.jwt.enabled来设置是否启用。
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2024/2/26
 */
@Component
public class MyJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String AUTH_HEADER = "authorization";

    @Value("${my.jwt.token:123123}")
    private String jwtToken;

    @Value("${my.authorization.jwt.enabled:true}")
    private boolean enabled;

    @Value("${my.authorization.path.ignores:}")
    private CharSequence[] ignoresArr;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeaderVal = request.getHeader(AUTH_HEADER);
        String token = StringUtils.substring(authHeaderVal, 7);

        if(!jwtToken.equals(token)){
            throw new UnauthorizedException();
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if(!enabled) return true;

        String url = request.getRequestURI();
        return StringUtils.startsWithAny(url, "/actuator", "/swagger", "/v3/api-docs") || StringUtils.startsWithAny(url, ignoresArr);
    }
}
