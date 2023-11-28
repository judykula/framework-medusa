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

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * <p>
 *     log输出请求url
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/24
 */
@Slf4j
public class MyAccessLogWebFilter implements WebFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String url = exchange.getRequest().getURI().toString();
        String headers = exchange.getRequest().getHeaders().toString();
        String params = exchange.getRequest().getQueryParams().toString();

        log.debug("【REQ200】【{}】【{}】【{}】", url, headers, params);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
