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
package com.jwy.medusa.consul;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     处理consul注册时的本地化策略
 *
 *     文档看这里{@code https://docs.spring.io/spring-cloud-consul/docs/3.1.4/reference/html/}
 * </p>
 *
 * @see ConsulServiceRegistryAutoConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/27
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.consul", matchIfMissing = true)
@AutoConfigureBefore(ServiceRegistryAutoConfiguration.class)
public class MyConsulConfiguration {

    @Bean
    public ConsulServiceRegistry consulServiceRegistry(ConsulClient consulClient, ConsulDiscoveryProperties properties,
                                                       HeartbeatProperties heartbeatProperties, @Autowired(required = false) TtlScheduler ttlScheduler) {
        return new MyConsulServiceRegistry(consulClient, properties, ttlScheduler, heartbeatProperties);
    }

}
