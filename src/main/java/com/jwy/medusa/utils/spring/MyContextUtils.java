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

    @Autowired
    private SpringContextUtils springContextUtils;

    @Autowired
    private JsonUtils jsonUtils;

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
}
