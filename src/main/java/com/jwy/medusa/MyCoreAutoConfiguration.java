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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jwy.medusa.consul.MyConsulConfiguration;
import com.jwy.medusa.feature.MyFeatureConfiguration;
import com.jwy.medusa.feign.MyFeignConfiguration;
import com.jwy.medusa.listener.AppStartedListener;
import com.jwy.medusa.loadbalance.MyLoadBalancerConfiguration;
import com.jwy.medusa.mvc.MyErrorAttributes;
import com.jwy.medusa.mvc.MyMvcConfiguration;
import com.jwy.medusa.saas.MySaaSConfiguration;
import com.jwy.medusa.utils.spring.JsonUtils;
import com.jwy.medusa.utils.spring.MyContextUtils;
import com.jwy.medusa.utils.spring.SpringContextUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

/**
 * <p>
 *     medusa是"my"微服务架构的核心内容实现
 *
 *     这里包含了框架核心逻辑的定制化扩展，比如：
 *     <pre>
 *     consul注册{@link MyMvcConfiguration}
 *     MVC请求&异常机制处理{@link MyMvcConfiguration}
 *     上下文Context工具{@link MyContextUtils}
 *     关于JSON工具的定义等{@link JsonUtils}
 *     </pre>
 * </p>
 * <p>
 *     并不建议各个微服务定义"通用的"aop或者listener组件
 * </p>
 * <p>
 *     关于{@link JsonUtils}，如果有必要的话，子项目可以自己重新定义，或者重新定义{@link ObjectMapper}
 *     我们是支持覆盖的。
 * </p>
 * <p>
 *     {@code @AutoConfigureBefore(ErrorMvcAutoConfiguration.class)}这句的作用是因为在{@link MyMvcConfiguration}中定义的
 *     {@link MyErrorAttributes}是需要覆盖掉其"内"原有的{@link org.springframework.boot.web.reactive.error.ErrorAttributes}的，
 *     所以必须在其初始化之前初始化
 * </p>
 *
 * @see MyConsulConfiguration
 * @see MyMvcConfiguration
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/10/26
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@Import({
        MyConsulConfiguration.class,
        MyMvcConfiguration.class,
        MyFeignConfiguration.class,
        MySaaSConfiguration.class,
        MyFeatureConfiguration.class,
        MyLoadBalancerConfiguration.class
})
public class MyCoreAutoConfiguration {

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - - - - - - - - -      UTILS   - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

    @Bean
    public MyContextUtils myContextUtils(){
        return new MyContextUtils();
    }

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonUtils jsonUtils(){
        return new JsonUtils();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - - - - - - - - -      App Started   - - -- - - - - - - - - - - - - - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

    @Bean
    public AppStartedListener appStartedListener(){
        return new AppStartedListener();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - -  -  - - - - - - - -      JSON      - - -- - - - - - - - - - - - - - - - - - - - - - - - - - //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

     //json custom
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,SerializationFeature.FAIL_ON_EMPTY_BEANS,
                        JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                .timeZone(TimeZone.getTimeZone("GMT+8"));
    }



}
