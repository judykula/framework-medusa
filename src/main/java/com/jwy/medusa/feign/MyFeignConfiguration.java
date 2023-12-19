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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     扩展feign
 * </p>
 * <p>
 *     默认是开启状态，你可以通过配置{@code my.extension.feign=false}来关闭功能支持
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/14
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.feign", matchIfMissing = true)
public class MyFeignConfiguration {

    @Bean
    public MyErrorDecoder myErrorDecoder(){
        return new MyErrorDecoder();
    }

}
