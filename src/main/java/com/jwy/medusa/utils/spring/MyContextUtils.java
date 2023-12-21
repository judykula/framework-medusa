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
package com.jwy.medusa.utils.spring;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.jwy.medusa.saas.Tenant;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *     自定义"上下文"工具类，关于框架内生命周期内的自定义数据传递，以及一些需要在spring内使用的"utils"都放这里
 * </p>
 * <p>
 *     比如:
 *     <pre>
 *         ...
 *         {@code @Autowired}
 *         private MyContextUtils myContextUtils;
 *         ...
 *         myContextUtils.springContext.xxx();
 *         ...
 *     </pre>
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2023/12/6
 */
public class MyContextUtils {

    /** "租户"信息上下文记录*/
    private TransmittableThreadLocal<Tenant> tenantContext = new TransmittableThreadLocal<>();
    /** "灰度"信息上下文记录*/
    private TransmittableThreadLocal<String> featureContext = new TransmittableThreadLocal<>();

    @Autowired
    private JsonUtils jsonUtils;
    @Autowired
    private SpringContextUtils springContextUtils;

    /**
     * 等同于注入并引用{@link SpringContextUtils}
     * @return SpringContextUtils
     */
    public SpringContextUtils springContext() {
        return springContextUtils;
    }

    /**
     * 等同于注入并引用{@link JsonUtils}
     * @return JsonUtils
     */
    public JsonUtils jsonUtils() {
        return jsonUtils;
    }

    /**
     * 从系统上下文中获取Tenant信息
     * @return {@link Tenant}
     */
    public Tenant getTenant(){
        return tenantContext.get();
    }

    /**
     * 设置Tenant上下文信息
     * @param tenant
     */
    public void setTenant(Tenant tenant){
        tenantContext.set(tenant);
    }

    public String getFeature(){
        return featureContext.get();
    }
    public void setFeature(String feature){
        featureContext.set(feature);
    }

    /**
     * 移除context中的的tenant内容
     *
     * 在"系统线程池（比如request）"的场景中，如果不想深入介入整个生命周期，
     * 我们必须在每个周期的间隙"重置"context数据，避免数据错误
     */
    public void clearTenantContext(){
        tenantContext.remove();
    }

    /**
     * 移除context中的的feature内容
     *
     * 在"系统线程池（比如request）"的场景中，如果不想深入介入整个生命周期，
     * 我们必须在每个周期的间隙"重置"context数据，避免数据错误
     */
    public void clearFeatureContext(){
        featureContext.remove();
    }
}
