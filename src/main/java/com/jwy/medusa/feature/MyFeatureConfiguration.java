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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *      灰度/路由扩展配置支持
 * </p>
 * <p>
 *     默认开启状态，可以设置{@code my.extension.feature=false}来关闭saas功能
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.feature", matchIfMissing = true)
public class MyFeatureConfiguration {

    @Bean
    public MyFeatureFilter myFeatureFilter(){
        return new MyFeatureFilter();
    }

    @Bean
    public MyFeatureInterceptor myFeatureInterceptor(){
        return new MyFeatureInterceptor();
    }
}
