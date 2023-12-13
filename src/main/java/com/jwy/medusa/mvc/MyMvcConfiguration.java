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
package com.jwy.medusa.mvc;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebFilter;

import java.util.stream.Collectors;

/**
 * <p>
 *     配置可插拔模块：MVC。
 *     这个是webflux的实现，文档在这里https://docs.spring.io/spring-boot/docs/2.7.17/reference/html/web.html#web.reactive.webflux
 * </p>
 * <p>
 *      包含所有请求的log记录实现，参考{@link MyAccessLogWebFilter}
 * </p>
 * <p>
 *     包含自定义异常处理，针对微服务对内，对外请求的消息异常消息处理 参考{@link MyErrorAttributes} {@link MyErrorWebExceptionHandler}
 * </p>
 * <p>
 *     相关源码看{@link ErrorWebFluxAutoConfiguration}
 * </p>
 *
 * @see ErrorWebFluxAutoConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/23
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.mvc", matchIfMissing = true)
@EnableConfigurationProperties({ ServerProperties.class, WebProperties.class })
public class MyMvcConfiguration {

    private final ServerProperties serverProperties;

    public MyMvcConfiguration(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    // see CodecsAutoConfiguration
    //@Bean
    //public CodecCustomizer myCodecCustomizer() {
    //    return (configurer) -> {
    //        configurer.registerDefaults(false);
    //        //configurer.customCodecs().register(new ServerSentEventHttpMessageReader());
    //        //configurer.customCodecs().register(new ServerSentEventHttpMessageWriter());
    //        // ...
    //    };
    //}

    @Bean
    public WebFilter myAccessLogWebFilter(){
        return new MyAccessLogWebFilter();
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new MyErrorAttributes();
    }

    @Bean
    @Order(-1)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes,
                                                             WebProperties webProperties, ObjectProvider<ViewResolver> viewResolvers,
                                                             ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
        MyErrorWebExceptionHandler exceptionHandler = new MyErrorWebExceptionHandler(errorAttributes,
                webProperties.getResources(), this.serverProperties.getError(), applicationContext);

        exceptionHandler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());

        return exceptionHandler;
    }

}
