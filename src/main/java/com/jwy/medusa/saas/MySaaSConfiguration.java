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

import com.jwy.medusa.common.utils.spring.MyContextUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     SaaS的配置入口
 *     主要包含"租户"信息的上下文传递,由{@link MySaasFilter} {@link MySaasInterceptor} {@link MyContextUtils} 消息链传递{@link Tenant}的json信息
 * </p>
 * <p>
 *     默认开启状态，可以设置{@code my.extension.saas=false}来关闭saas功能
 * </p>
 * <p>
 *     SaaS信息以{@link Tenant}承载元信息，流程如下
 *     <pre>
 *         api request -> MySaaSFilter -> MySaasInterceptor -> MyContext -> DbConnector
 *                              |`````````````````````````````````````````````````````` |
 *                              |· · · ·· · ···· · ··· ··· · ··· ··· · ·· · ·     architect   · · ·· · ·· · ··· ··· · ··· ··· · ··· ·· · ·|
 *                              |_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _|
 *     </pre>
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.saas", matchIfMissing = true)
public class MySaaSConfiguration {

    @Bean
    public MySaasFilter mySaasFilter(){
        return new MySaasFilter();
    }

    @Bean
    public MySaasInterceptor mySaasInterceptor(){
        return new MySaasInterceptor();
    }

}
