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

import com.ecwid.consul.v1.ConsulClient;
import com.jwy.medusa.feature.FeatureTags;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.core.env.PropertyResolver;

/**
 * <p>
 *      自定义注册consul时的处理
 * </p>
 * <p>
 *     主要是设置consul "instance_id"的组成，以及添加默认的metadata<br><br>
 *
 *     这两点也可以通过spring-consul提供的properties配置完成，之所以在这里写代码: <br>
 *     1. 防止因下面的项目维护人员胡乱定义/修改 <br>
 *     2. 自定义更令话以及扩展，可读性也更强 <br>
 *     <br>
 *     如果你仍需要自定义，可以参考文档{@code https://docs.spring.io/spring-cloud-consul/docs/3.1.4/reference/html/appendix.html}
 * </p>
 * <p>
 *     关于这里自定义的consul service instanceId ，可以系统下面这个spring 实现
 *     {@link org.springframework.cloud.commons.util.IdUtils#getDefaultInstanceId(PropertyResolver)}
 *
 *     因为在这里自定义构成了，所以{@code vcap.application.instance_id} 以及 {@code spring.application.instance_id}的配置则不起作用
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/25
 */
public class MyConsulServiceRegistry extends ConsulServiceRegistry {

    public MyConsulServiceRegistry(ConsulClient client, ConsulDiscoveryProperties properties, TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
        super(client, properties, ttlScheduler, heartbeatProperties);
    }

    @Override
    public void register(ConsulRegistration reg) {

        /*自定义consul 的service instanceId*/
        StringBuffer id = new StringBuffer();
        id.append(reg.getService().getName()).append(":")
                .append(reg.getService().getAddress()).append(":")
                .append(reg.getService().getPort());
        reg.getService().setId(id.toString());

        //metadata & tag
        String feature = reg.getService().getMeta().putIfAbsent(FeatureTags.FEATURE_TAG_KEY, FeatureTags.FEATURE_VALUE_DEFAULT);
        String date = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(System.currentTimeMillis());
        reg.getService().getTags().add(feature);
        reg.getService().getTags().add(date);

        //TODO 处理注册时的问题

        super.register(reg);
    }
}
