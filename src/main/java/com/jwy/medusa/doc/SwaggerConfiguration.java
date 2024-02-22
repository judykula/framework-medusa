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
package com.jwy.medusa.doc;

import com.jwy.medusa.common.utils.MyHttpHeaders;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 *     spring doc 配置
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2024/2/20
 */
//@Profile("!prod")
@Configuration(proxyBeanMethods = false)
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    /**就是这个 "com.jwy" */
    private final String _GroupId;

    public SwaggerConfiguration() {
        String packageName = SwaggerConfiguration.class.getPackage().getName();
        _GroupId = StringUtils.substringBefore(packageName, ".medusa");
    }

    @Bean
    public GroupedOpenApi webMvcApi() {
        return GroupedOpenApi.builder().group("WebMvc")
                .packagesToScan(String.format("%s.%s.web", _GroupId, applicationName))
                .build();
    }

    @Bean
    public GroupedOpenApi feignApi() {
        return GroupedOpenApi.builder().group("Feign")
                .packagesToScan(String.format("%s.%s.feign.server", _GroupId, applicationName))
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
                .title("【"+this.applicationName+"】API文档")
                .description("if you want custom your doc, see https://github.com/springdoc/springdoc-openapi-demos/blob/v3.1.5/springdoc-openapi-spring-boot-2-webmvc/src/main/resources/application.yml")
                .version("1.0.0");

        return new OpenAPI()
                .components( new Components()
                        //.addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                        .addParameters("GlobalTenant", new HeaderParameter().required(true)
                                .name(MyHttpHeaders.REQUEST_SAAS_TENANT)
                                .description("空间/租户")
                                .addExample("DEFAULT", new Example().value("default"))
                                .schema(new StringSchema())
                        )
                        .addParameters("GlobalClientId", new HeaderParameter().required(true)
                                .name(MyHttpHeaders.REQUEST_CLIENT_ID)
                                .description(MyHttpHeaders.REQUEST_CLIENT_ID_DESC)
                                .addExample("PC", new Example().value("pc"))
                                .schema(new StringSchema())
                        )
                        .addParameters("GlobalToken", new HeaderParameter().required(true)
                                .name(MyHttpHeaders.REQUEST_ACCESS_TOKEN)
                                .description("访问token")
                                .addExample("TEST", new Example().value("3545c885d0a74"))
                                .schema(new StringSchema())
                        )
                        .addParameters("GlobalDeviceId", new HeaderParameter().required(false)
                                .name(MyHttpHeaders.REQUEST_DEVICE_ID)
                                .description("设备ID，APP请求时添加")
                                .addExample("TEST", new Example().value("11111"))
                                .schema(new StringSchema())
                        )
                )
                .info(info);
    }

    @Bean
    public GlobalOperationCustomizer globalOperationCustomizer(){
        return (operation, handlerMethod) -> operation
                .addParametersItem(new HeaderParameter().$ref("GlobalTenant"))
                .addParametersItem(new HeaderParameter().$ref("GlobalClientId"))
                .addParametersItem(new HeaderParameter().$ref("GlobalToken"))
                .addParametersItem(new HeaderParameter().$ref("GlobalDeviceId"));
    }
}
