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

import com.jwy.medusa.common.utils.MyHttpHeaders;
import com.jwy.medusa.common.utils.spring.MyContextUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *     将需要透传的"SaaS头信息"增加到Feign请求上下文中
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Slf4j
public class MySaasInterceptor implements RequestInterceptor {

    @Autowired
    private MyContextUtils myContextUtils;

    @Override
    public void apply(RequestTemplate template) {

        Tenant tenant = this.myContextUtils.getTenant();
        if (tenant == null) return;

        try {
            String tenantJson = this.myContextUtils.jsonUtils().toString(tenant);
            template.header(MyHttpHeaders.REQUEST_SAAS_TENANT, tenantJson);
        } catch (Exception e) {
            log.error("【MSF046】tenant deserialize fail", e);
        }

        log.debug("【MSI049】feign request headers : {}", template.headers());
    }

}
