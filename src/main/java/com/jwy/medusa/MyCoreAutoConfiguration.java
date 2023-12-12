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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwy.medusa.consul.MyConsulConfiguration;
import com.jwy.medusa.listener.AppStartedListener;
import com.jwy.medusa.mvc.MyErrorAttributes;
import com.jwy.medusa.mvc.MyMvcConfiguration;
import com.jwy.medusa.utils.JsonUtils;
import com.jwy.medusa.utils.SpringContextUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 *     medusa是"my"微服务架构的核心内容实现
 *
 *     这里包含了框架核心逻辑的定制化扩展，比如：
 *     <pre>
 *     consul注册{@link MyMvcConfiguration}
 *     MVC请求&异常机制处理{@link MyMvcConfiguration}
 *     上下文Context工具{@link com.jwy.medusa.utils.MyContextUtils}
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
 *     {@code @AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)}这句的作用是因为在{@link MyMvcConfiguration}中定义的
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
@Import({MyConsulConfiguration.class, MyMvcConfiguration.class})
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
public class MyCoreAutoConfiguration {

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    public AppStartedListener appStartedListener(){
        return new AppStartedListener();
    }

    // json custom
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

    @Bean
    @ConditionalOnMissingBean
    public JsonUtils jsonUtils(){
        return new JsonUtils();
    }

}
