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
package com.jwy.medusa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jwy.medusa.consul.MyConsulConfiguration;
import com.jwy.medusa.listener.AppStartedListener;
import com.jwy.medusa.mvc.MyMvcConfiguration;
import com.jwy.medusa.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

/**
 * <p>
 *     fuxi项目的自动装配入口
 * </p>
 * <p>
 *     fuxi负责架构的核心代码实现，比如统计的listener、filter、aop等
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/26
 */
@Configuration(proxyBeanMethods = false)
@Import({MyConsulConfiguration.class, MyMvcConfiguration.class})
public class MyCoreAutoConfiguration {

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    public AppStartedListener appStartedListener(){
        return new AppStartedListener();
    }

    //@Bean
    //public ObjectMapper objectMapper() {
    //    ObjectMapper mapper = new ObjectMapper();
    //    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    //    mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    //    mapper.registerModule(new JavaTimeModule());
    //
    //    return mapper;
    //}

}
