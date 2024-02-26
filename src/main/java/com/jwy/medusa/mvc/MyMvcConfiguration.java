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
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

/**
 * <p>
 *     配置可插拔模块：MVC。<br>
 *     <s>这个是webflux的实现，文档在这里https://docs.spring.io/spring-boot/docs/2.7.17/reference/html/web.html#web.reactive.webflux</s><br>
 *     这个是web mvc的实现，文档在这里https://docs.spring.io/spring-boot/docs/2.7.18/reference/html/web.html#web.servlet.spring-mvc
 * </p>
 * <p>
 *      包含所有请求的log记录实现，参考{@link MyAccessLogFilter}
 * </p>
 * <p>
 *     包含自定义异常处理，针对微服务对内，对外请求的消息异常消息处理 参考{@link MyErrorAttributes} {@link MyBasicErrorController}
 * </p>
 * <p>
 *     相关源码看{@link ErrorMvcAutoConfiguration}
 * </p>
 *
 * @see ErrorMvcAutoConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/23
 */
@Configuration()
@ConditionalOnProperty(name = "my.extension.mvc", matchIfMissing = true)
@EnableConfigurationProperties({ ServerProperties.class})
public class MyMvcConfiguration {

    private final ServerProperties serverProperties;

    public MyMvcConfiguration(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public MyJwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new MyJwtAuthenticationTokenFilter();
    }

    @Bean
    public MyAccessLogFilter accessLogFilter() {
        return new MyAccessLogFilter();
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new MyErrorAttributes();
    }

    @Bean
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,
                                                     ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        return new MyBasicErrorController(errorAttributes, this.serverProperties.getError(),
                errorViewResolvers.orderedStream().collect(Collectors.toList()));
    }

    //see CodecsAutoConfiguration
    //@Bean
    //public CodecCustomizer myCodecCustomizer() {
    //    return (configurer) -> {
    //        configurer.registerDefaults(false);
    //        configurer.customCodecs().register(new ServerSentEventHttpMessageReader());
    //        configurer.customCodecs().register(new ServerSentEventHttpMessageWriter());
    //        // ...
    //    };
    //}
}
