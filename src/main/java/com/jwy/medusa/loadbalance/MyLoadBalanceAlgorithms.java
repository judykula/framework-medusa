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

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * <p>
 *     自定义lb算法。
 *     文档看这里{@code https://docs.spring.io/spring-cloud-commons/docs/3.1.8/reference/html/#switching-between-the-load-balancing-algorithms}
 * </p>
 * <p>
 *     自定义lb路由算法的目的是适配我们的"feature"灰度路由策略
 * </p>
 * <p>
 *     区别于{@link MyLoadBalancerConfiguration}, 本类不是一个"配置类"，参考这段话
 *     "The classes you pass as @LoadBalancerClient or @LoadBalancerClients configuration arguments
 *     should either not be annotated with @Configuration or be outside component scan scope."
 *
 *     所以，关于load balance的除了"路由算法"之外的配置 都要放到{@link MyLoadBalancerConfiguration}中
 * </p>
 *
 * @see LoadBalancerClientConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/22
 */
public class MyLoadBalanceAlgorithms {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
                                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new MyRoundRobinFeatureLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }


}
