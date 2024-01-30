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
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *     将需要透传的"灰度路由信息"增加到Feign请求上下文中
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Slf4j
public class MyFeatureInterceptor implements RequestInterceptor {

    @Autowired
    private MyContextUtils myContextUtils;

    @Override
    public void apply(RequestTemplate template) {
        String feature = this.myContextUtils.getFeature();
        if (StringUtils.isNotEmpty(feature)) {
            template.header(MyHttpHeaders.REQUEST_FEATURE, feature);
        }
        log.debug("【MFI043】feign request headers : {}" + template.headers());
    }

}
