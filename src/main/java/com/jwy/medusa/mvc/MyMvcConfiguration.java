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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerSentEventHttpMessageReader;
import org.springframework.http.codec.ServerSentEventHttpMessageWriter;

/**
 * <p>
 *     配置可插拔模块：MVC
 * </p>
 * <p>
 *
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/11/23
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.extension.mvc", matchIfMissing = true)
public class MyMvcConfiguration {

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




}
