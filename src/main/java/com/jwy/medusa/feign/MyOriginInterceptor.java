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
package com.jwy.medusa.feign;

import com.jwy.medusa.common.utils.MyHttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 *     为请求添加origin信息标记
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/19
 */
public class MyOriginInterceptor implements RequestInterceptor {

    @Value("${spring.application.name:}")
    private String application;

    @Override
    public void apply(RequestTemplate template) {
        template.header(MyHttpHeaders.REQUEST_ORIGIN, application);
    }
}
