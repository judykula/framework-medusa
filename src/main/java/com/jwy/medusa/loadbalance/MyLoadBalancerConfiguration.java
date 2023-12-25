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
package com.jwy.medusa.loadbalance;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     自定义lb配置，官方文档看这里：
 *     {@code https://docs.spring.io/spring-cloud-commons/docs/3.1.8/reference/html/#spring-cloud-loadbalancer}
 * </p>
 * <p>
 *     自定义的路由算法在这里{@link MyLoadBalanceAlgorithms}
 * </p>
 *
 * @see LoadBalancerClientConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/21
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.load-balance", matchIfMissing = true)
@LoadBalancerClient(value = "feature-lb-provider", configuration = MyLoadBalanceAlgorithms.class)
public class MyLoadBalancerConfiguration {

    /**
     * 指定使用 caching. D: 不指定也是默认的
     *
     * @param context
     * @return
     */
    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {

        //withCaching  withHealthChecks  withSameInstancePreference
        //只能选择一个，看需要自定义
        return ServiceInstanceListSupplier.builder()
                .withDiscoveryClient()
                .withCaching()
                //.withHealthChecks()
                //.withSameInstancePreference()
                .build(context);
    }

}