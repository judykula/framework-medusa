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

import com.jwy.medusa.utils.MyHttpHeaders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *      灰度/路由扩展配置支持
 * </p>
 * <p>
 *     默认开启状态，可以设置{@code my.extension.feature=false}来关闭灰度/路由功能
 * </p>
 * <p>
 *     整个系统的灰度流程如下图：
 *     <pre>
 *         api request -> gate way-> MyFeatureFilter -> MyContext -> MyFeatureInterceptor ->  MyLoadBalanceAlgorithms
 *                              |```````````````````````````````````````````````````````````````````````````````````` |
 *                              |· · · ·· · ···· · ··· ··· · ·· ···· ···· ···· ···· ···· ····· ··· · ·· · ·     architect   · · ·· · ·· · ··· ··· · ··· ···· ···· ···· ···· ···· ···· ···· ··· · ··· ·· · ·|
 *                              |_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _|
 *     </pre>
 *     有几个要求：<br>
 *     1. 需要在请求的时候(网管)添加流量控制header：{@link MyHttpHeaders#REQUEST_FEATURE}指定要分流的feature <br>
 *     2. 服务在注册到注册中心时（consul），需要指定tag:feature {@link FeatureTags#FEATURE_TAG_KEY}  <br>
 *     3. 这样根据{@link com.jwy.medusa.loadbalance.MyLoadBalanceAlgorithms MyLoadBalanceAlgorithms} 就能进行请求流量路由了 <br>
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
