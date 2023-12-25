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
package com.jwy.medusa.loadbalance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jwy.medusa.feature.FeatureTags;
import com.jwy.medusa.utils.spring.MyContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *     自定义load balance算法
 * </p>
 * <p>
 *     扩展{@link org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer RoundRobinLoadBalancer} 实现，
 *
 *     自定义加入feature逻辑
 * </p>
 *
 * @see RoundRobinLoadBalancer
 * @see MyRoundRobinLoadBalancerDuplication
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/22
 */
@Slf4j
public class MyRoundRobinFeatureLoadBalancer extends MyRoundRobinLoadBalancerDuplication {

    @Autowired
    private MyContextUtils myContextUtils;

    public MyRoundRobinFeatureLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        super(serviceInstanceListSupplierProvider, serviceId);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {

        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);

        return supplier.get(request).next().map(serviceInstances -> doFeatureProcess(supplier, serviceInstances));
    }

    /**
     * do process
     *
     * 在round robin算法的基础上进行 feature (灰度)路由
     *
     * @param supplier
     * @param serviceInstances
     * @return
     */
    private Response<ServiceInstance> doFeatureProcess(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {

        String tmpContextFeature = this.myContextUtils.getFeature();
        String contextFeature = StringUtils.defaultIfEmpty(tmpContextFeature, FeatureTags.FEATURE_VALUE_NORMAL).toLowerCase();

        /* 默认的feature value为"normal", 如果想灰度其他流量，可以设置对应discover tag与feature*/
        Map<String, List<ServiceInstance>> featureMap = Maps.newHashMap();
        for (ServiceInstance serviceInstance : serviceInstances) {
            String tmpServerFeature = serviceInstance.getMetadata().get(FeatureTags.FEATURE_TAG_KEY);
            String serverFeature = StringUtils.defaultIfEmpty(tmpServerFeature, FeatureTags.FEATURE_VALUE_NORMAL).toLowerCase();

            if(featureMap.containsKey(serverFeature))
                featureMap.put(serverFeature, Lists.newArrayList());
            featureMap.get(serverFeature).add(serviceInstance);
        }
        log.debug("【RRF090】contextFeature: {}, featureMap: {}", contextFeature, featureMap);

        List<ServiceInstance> serviceInstancesRes = featureMap.get(contextFeature);
        if(CollectionUtils.isEmpty(serviceInstancesRes)) {
            // 如果没有相同特征信息的，就从正常实例中选取
            serviceInstancesRes = featureMap.get(FeatureTags.FEATURE_VALUE_NORMAL);
        }

        return super.processInstanceResponse(supplier, serviceInstancesRes);
    }
}
