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

import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *     处理consul注册时的本地化策略
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/27
 */
public class MyConsulConfiguration {

    @Bean
    public ConsulAutoServiceRegistration consulAutoServiceRegistration(ConsulServiceRegistry registry,
                                                                       AutoServiceRegistrationProperties autoServiceRegistrationProperties, ConsulDiscoveryProperties properties,
                                                                       ConsulAutoRegistration consulRegistration) {

        System.out.println(consulRegistration);
        //TODO

        return new ConsulAutoServiceRegistration(registry, autoServiceRegistrationProperties, properties,
                consulRegistration);
    }


}
