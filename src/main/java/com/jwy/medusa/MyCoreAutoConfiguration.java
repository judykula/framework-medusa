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

import com.jwy.medusa.consul.MyConsulConfiguration;
import com.jwy.medusa.listener.AppStartedListener;
import com.jwy.medusa.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
@Import({MyConsulConfiguration.class})
public class MyCoreAutoConfiguration {

    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    public AppStartedListener appStartedListener(){
        return new AppStartedListener();
    }

}
