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
 *     扩展feign，支持请求header内容传递，包括添加"请求源"，以及实现异常机制的传递(具体逻辑看{@link MyErrorDecoder})
 *
 *     相关文档看这里：{@code https://docs.spring.io/spring-cloud-openfeign/docs/3.1.8/reference/html/#spring-cloud-feign}
 * </p>
 * <p>
 *     默认是开启状态，你可以通过配置{@code my.extension.feign=false}来关闭功能支持.<br>
 *     <i>！不要关闭此功能，除非你不想使用feign来实现内部调用的rpc。</i>
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

    @Bean
    public MyOriginInterceptor myOriginInterceptor(){
        return new MyOriginInterceptor();
    }
}
